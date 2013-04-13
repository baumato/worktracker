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
package org.tobbaumann.wt.statusline;

import java.text.DateFormat;

import javax.inject.Inject;

import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.domain.WorkItem;
import org.tobbaumann.wt.ui.event.Events;

import com.google.common.base.Optional;

public class StatusLine extends Composite {

	private static final int UPDATE_FREQUENCY_IN_MILLIS = 1000; // TODO take update frequency from preferences
	private WorkTrackingService service;
	private Label statusBar;


	@Inject
	public StatusLine(Composite parent, WorkTrackingService service) {
		super(parent, SWT.NONE);
		this.service = service;
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

	@Inject @org.eclipse.e4.core.di.annotations.Optional
	void workItemStarted(@UIEventTopic(Events.START_WORK_ITEM) Optional<WorkItem> optStartedWorkItem) {
		if (isStatusLineActive() || !optStartedWorkItem.isPresent()) {
			setStatusLineMessageFromActiveWorkItem(optStartedWorkItem);
			updateStatusLinePeriodically();
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
		return null;
	}

	private void updateStatusLinePeriodically() {
		getDisplay().timerExec(UPDATE_FREQUENCY_IN_MILLIS, new Runnable() {
			@Override
			public void run() {
				if (isStatusLineActive()) {
					setStatusLineMessageFromActiveWorkItem(service.getActiveWorkItem());
					updateStatusLinePeriodically();
				}
			}
		});
	}
}
