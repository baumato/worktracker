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

import static com.google.common.collect.Lists.newArrayList;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SubMonitor;
import org.tobbaumann.wt.core.WorkTrackingService.CreationResult;
import org.tobbaumann.wt.core.WorkTrackingService.OperationCanceledException;
import org.tobbaumann.wt.domain.Activity;
import org.tobbaumann.wt.domain.DomainFactory;
import org.tobbaumann.wt.domain.WorkItem;

import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.io.Resources;

class FakeDataCreator {

	private final Random random = new Random();
	private final WorkTrackingServiceImpl service;

	FakeDataCreator(WorkTrackingServiceImpl service) {
		this.service = service;
	}

	CreationResult createFakeData(int numberOfDays, IProgressMonitor monitor) {
		SubMonitor progress = SubMonitor.convert(monitor, "Creating fake data", 100);
		List<Activity> activities = createActivities(progress.newChild(20));
		ImmutableSet<WorkItem> workItems = createItems(numberOfDays, activities,
				progress.newChild(70));
		service.addActivities(activities);
		progress.worked(5);
		service.addWorkItems(workItems);
		progress.worked(5);
		return new CreationResult(activities.size(), workItems.size(),
				Collections.<IStatus> emptyList());
	}

	private List<Activity> createActivities(IProgressMonitor monitor) {
		List<Activity> res = newArrayList();
		List<String> activities = createFakeActivities();
		SubMonitor progress = SubMonitor.convert(monitor, "Creating activites", activities.size());
		for (String name : activities) {
			Activity activity = service.createActivityInternal(name);
			activity.setOccurrenceFrequency(random.nextInt(30));
			res.add(activity);
			progress.worked(1);
		}
		return res;
	}

	private List<String> createFakeActivities() {
		return readLinesOfFile("FakeActivities.txt");
	}

	private ImmutableSet<WorkItem> createItems(int numberOfDays, List<Activity> activities, IProgressMonitor monitor) {
		SubMonitor progress = SubMonitor.convert(monitor, "Creating work items", numberOfDays);
		ImmutableSet.Builder<WorkItem> items = ImmutableSet.builder();
		List<String> descriptions = createFakeDescripitons();
		Calendar cal = Calendar.getInstance();
		for (int i = numberOfDays; i > 0; i--) {
			if (progress.isCanceled()) {
				throw new OperationCanceledException();
			}
			progress.subTask("Day " + (numberOfDays-i+1));
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
			omitWeekend(cal);
			setHourAndRandomMinute(cal, 9);
			Date startDate = cal.getTime();
			for (int hourOfDay = 10; hourOfDay <= 19; hourOfDay++) {
				setHourAndRandomMinute(cal, hourOfDay);
				Date endDate = cal.getTime();
				Activity activity = activities.get(random.nextInt(activities.size()));
				String description = descriptions.get(random.nextInt(descriptions.size()));
				items.add(createWorkItem(activity, startDate, endDate, description));
				startDate = endDate;
			}
			progress.worked(1);
		}
		return items.build();
	}

	private List<String> createFakeDescripitons() {
		return readLinesOfFile("FakeDescriptions.txt");
	}

	private List<String> readLinesOfFile(String fileName) {
		List<String> lines = ImmutableList.of();
		try {
			URL resource = Resources.getResource(FakeDataCreator.class, fileName);
			lines = newArrayList(Resources.readLines(resource, Charsets.UTF_8));
		} catch (IOException e) {
			Throwables.propagate(e);
		}
		Iterator<String> linesIter = lines.iterator();
		while (linesIter.hasNext()) {
			String line = linesIter.next();
			if (line.startsWith("#")) {
				linesIter.remove();
			}
		}
		return lines;
	}

	private void omitWeekend(Calendar cal) {
		while(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
		}
	}

	private void setHourAndRandomMinute(Calendar cal, int hourOfDay) {
		cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		cal.set(Calendar.MINUTE, random.nextInt(60));
	}

	private WorkItem createWorkItem(Activity activity, Date start, Date end, String description) {
		WorkItem wi = DomainFactory.eINSTANCE.createWorkItem();
		wi.setActivity(activity);
		wi.setStart(start);
		wi.setEndDate(end);
		wi.setDescription(description);
		return wi;
	}
}
