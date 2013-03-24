/*******************************************************************************
 * Copyright (c) 2013 Tobias Baumann.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Tobias Baumann - initial API and implementation
 ******************************************************************************/
package org.tobbaumann.wt.core.impl;

import static com.google.common.base.Strings.emptyToNull;
import static com.google.common.collect.Lists.newArrayList;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.tobbaumann.wt.core.WorkTrackingService.ImportResult;
import org.tobbaumann.wt.core.WorkTrackingService.OperationCanceledException;
import org.tobbaumann.wt.domain.Activity;
import org.tobbaumann.wt.domain.WorkItem;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.google.common.base.Optional;
import com.google.common.base.Throwables;

class WorkTrackerDataImporter {

	private final WorkTrackingServiceImpl service;

	WorkTrackerDataImporter(WorkTrackingServiceImpl service) {
		this.service = service;
	}

	ImportResult importData(String strPath, IProgressMonitor monitor) {
		try {
			Path path = Paths.get(strPath);
			SubMonitor progress = SubMonitor.convert(monitor, "Import data from directory " + path.getFileName(), 100);
			DirectoryScanner scanner = new DirectoryScanner(path);
			XmlFileProcessor fp = new XmlFileProcessor();
			scanner.scanDirectoryRecursively(fp, progress.newChild(100));
			service.addActivities(fp.handler.activities);
			service.addWorkItems(fp.handler.workItems);
			int activitiesSize = fp.handler.activities.size();
			int workItemsSize = fp.handler.workItems.size();
			return new ImportResult(activitiesSize, workItemsSize, fp.errors);
		} catch (Exception e) {
			Throwables.propagateIfPossible(e);
			throw new RuntimeException(e);
		}
	}

	/**
	 *
	 * @author tobbaumann
	 *
	 */
	private final class DirectoryScanner {
		private final Path path;

		DirectoryScanner(Path path) {
			this.path = path;
		}

		public void scanDirectoryRecursively(final FileProcessor fp, SubMonitor progress) throws IOException {
			scanDirectory(path, fp, progress);
		}

		private void scanDirectory(Path path, final FileProcessor fp, final SubMonitor progress) throws IOException {
			if (progress.isCanceled()) {
				throw new OperationCanceledException();
			}
			DirectoryStream<Path> ds = Files.newDirectoryStream(path,
					new DirectoryStream.Filter<Path>() {
						@Override
						public boolean accept(Path p) throws IOException {
							if (Files.isDirectory(p)) {
								scanDirectory(p, fp, progress);
								return false;
							}
							String fileName = p.getFileName().toString();
							return fileName.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}.xml");
						}
					});
			for (Path p : ds) {
				progress.subTask(p.toString());
				progress.setWorkRemaining(100);
				fp.processFile(p);
				progress.worked(1);
			}
		}
	}

	/**
	 *
	 * @author tobbaumann
	 *
	 */
	private static interface FileProcessor {
		void processFile(Path p);
	}

	/**
	 *
	 * @author tobbaumann
	 *
	 */
	private final class XmlFileProcessor implements FileProcessor {

		private final SAXParserFactory factory = SAXParserFactory.newInstance();
		private final WorkTrackerFileHandler handler = new WorkTrackerFileHandler();
		private final List<IStatus> errors = newArrayList();

		@Override
		public void processFile(final Path p) {
			try {
				SAXParser saxParser = factory.newSAXParser();
				saxParser.parse(p.toFile(), handler);
			} catch (Exception e) {
				errors.add(createErrorStatus(p, e));
			}
		}

		private Status createErrorStatus(final Path p, Exception e) {
			return new Status(IStatus.ERROR, WorkTrackerDataImporter.class.getSimpleName(),
					"Error importing file " + p.getFileName() + ":\n" + Throwables.getStackTraceAsString(e), e);
		}
	}

	/**
	 * Reads the old WorkTrackerData in format:
	 *
	 * <activity name="Reporting">
	 * <start>20.09.2010 11:01:41</start>
	 * <end>20.09.2010 11:57:18</end>
	 * <description />
	 * </activity>
	 *
	 * @author tobbaumann
	 */
	private final class WorkTrackerFileHandler extends DefaultHandler {

		private static final String ACTIVITY_ELEMENT = "activity";
		private static final String ACTIVITY_NAME_ATTRIBUTE = "name";
		private static final String START_ELEMENT = "start";
		private static final String END_ELEMENT = "end";
		private static final String DESCRIPTION_ELEMENT = "description";

		private StringBuilder elementContent;
		private Activity activity;
		private String start;
		private String end;
		private String description;

		List<Activity> activities = newArrayList();
		List<WorkItem> workItems = newArrayList();

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {
			elementContent = new StringBuilder();
			if (isActivityElement(qName)) {
				String activityName = attributes.getValue(ACTIVITY_NAME_ATTRIBUTE);
				Optional<Activity> a = service.getActivity(activities, activityName);
				if (a.isPresent()) {
					activity = a.get();
				} else {
					activity = service.createActivityInternal(activityName);
					activities.add(activity);
				}
				resetWorkItemFields();
			}
		}

		private void resetWorkItemFields() {
			start = null;
			end = null;
			description = null;
		}

		private boolean isActivityElement(String qName) {
			return qName.equals(ACTIVITY_ELEMENT);
		}

		@Override
		public void characters(char ch[], int start, int length) throws SAXException {
			elementContent.append(ch, start, length);
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			switch (qName) {
			case ACTIVITY_ELEMENT:
				WorkItem wi = service.createWorkItemInternal(activity, createDate(start),
						createDate(end), emptyToNull(description));
				workItems.add(wi);
				break;
			case START_ELEMENT:
				start = elementContent.toString();
				break;
			case END_ELEMENT:
				end = elementContent.toString();
				break;
			case DESCRIPTION_ELEMENT:
				description = elementContent.toString();
				break;
			default:
				break;
			}
		}

		private Date createDate(String strDate) {
			try {
				SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
				Date d = format.parse(strDate);
				return d;
			} catch (Exception e) {
				Throwables.propagateIfPossible(e);
				throw new RuntimeException(e);
			}
		}
	}
}
