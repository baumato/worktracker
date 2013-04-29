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
package org.tobbaumann.wt.ui.statusline;

import java.text.DateFormat;

import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.domain.WorkItem;
import org.tobbaumann.wt.ui.event.Events;
import org.tobbaumann.wt.ui.preferences.WorkTrackerPreferences;

import com.google.common.base.Optional;

public class StatusLine extends Composite {

	private WorkTrackingService service;
	private Label statusBar;

	private long updateFrequencyInMillis; // initialized over preferences
	private final Job updateJob;

	@Inject
	public StatusLine(Composite parent, WorkTrackingService service) {
		super(parent, SWT.NONE);
		this.service = service;
		this.updateJob = new StatusLineUpdateJob();
		createControl();
	}

	private void createControl() {
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		FillLayout fillLayout = new FillLayout();
		fillLayout.marginHeight = 3;
		fillLayout.marginWidth = 3;
		setLayout(fillLayout);
		statusBar = new Label(this, SWT.NONE);
		statusBar.setAlignment(SWT.RIGHT);
	}

	@Inject
	@org.eclipse.e4.core.di.annotations.Optional
	public void updateUpdateFrequencyInMillis(@Preference(value = WorkTrackerPreferences.STATUS_LINE_UPDATE_FREQUENCY) long newUpdateFreq) {
		if (updateFrequencyInMillis != newUpdateFreq) {
			this.updateFrequencyInMillis = newUpdateFreq;
			updateJob.cancel();
			updateJob.schedule(updateFrequencyInMillis);
		}
	}

	@Inject @org.eclipse.e4.core.di.annotations.Optional
	void workItemStarted(@UIEventTopic(Events.START_WORK_ITEM) Optional<WorkItem> optStartedWorkItem) {
		if (isStatusLineActive() || !optStartedWorkItem.isPresent()) {
			setStatusLineMessageFromActiveWorkItem(optStartedWorkItem);
			updateJob.schedule(this.updateFrequencyInMillis);
		}
	}

	private boolean isStatusLineActive() {
		return !isDisposed();
	}

	private void setStatusLineMessageFromActiveWorkItem(Optional<WorkItem> optStartedWorkItem) {
		setMessage(createStatusLineMessage(optStartedWorkItem));
	}

	private void setMessage(String s) {
		statusBar.setText(s);
		pack(true);
	}


	private String createStatusLineMessage(Optional<WorkItem> optStartedWorkItem) {
		if (optStartedWorkItem.isPresent()) {
			WorkItem startedWorkItem = optStartedWorkItem.get();
			return String.format(
					"Working on \"%s\" since %s for %s now.",
					startedWorkItem.getActivityName(),
					startedWorkItem.formatStart(DateFormat.getTimeInstance()),
					startedWorkItem.getDuration().toString());
		}
		return "";
	}

	/**
	 *
	 * @author tobbaumann
	 *
	 */
	private final class StatusLineUpdateJob extends Job {

		public StatusLineUpdateJob() {
			super("StatusLine Update");
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			monitor.beginTask("Updating status line...", IProgressMonitor.UNKNOWN);
			try {
				if (statusBar.isDisposed()) {
					return Status.OK_STATUS;
				}
				statusBar.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						updateStatusLine();
					}
				});
			} finally {
				monitor.done();
			}
			return Status.OK_STATUS;
		}

		private void updateStatusLine() {
			if (isStatusLineActive()) {
				setStatusLineMessageFromActiveWorkItem(service.getActiveWorkItem());
				updateJob.schedule(updateFrequencyInMillis);
			}
		}
	}
}
