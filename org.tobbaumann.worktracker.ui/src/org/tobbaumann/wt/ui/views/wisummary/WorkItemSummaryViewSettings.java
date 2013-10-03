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
package org.tobbaumann.wt.ui.views.wisummary;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.tobbaumann.wt.ui.preferences.WorkTrackerPreferences;
import org.tobbaumann.wt.ui.preferences.WorkTrackerPreferences.TimePeriod;
import org.tobbaumann.wt.ui.views.PreferencesComposite;

public class WorkItemSummaryViewSettings extends PreferencesComposite {

	private Button btnDaily;
	private Button btnWeekly;
	private Spinner spinnerHrsDay;
	private Spinner spinnerHrsWeekly;

	private TimePeriod summaryTimePeriod;
	private Integer maxDailyHours;
	private Integer maxWeeklyHours;

	public WorkItemSummaryViewSettings(Composite parent, WorkTrackerPreferences prefs) {
		super(parent, prefs);
		updatePreferenceFieldsInternal();
		createControl();
	}

	private void createControl() {
		setLayout(new GridLayout(2, false));
		createKindGroup();
		createMaxHrsPerTimeSpanGroup();
	}

	private void createKindGroup() {
		Label lblDaily = new Label(this, SWT.NONE);
		lblDaily.setText("Show daily summary");
		btnDaily = new Button(this, SWT.RADIO);
		btnDaily.setSelection(summaryTimePeriod == TimePeriod.DAILY);
		btnDaily.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				summaryTimePeriod = btnDaily.getSelection() ? TimePeriod.DAILY : TimePeriod.WEEKLY;
			}
		});

		Label lblWeekly = new Label(this, SWT.NONE);
		lblWeekly.setText("Show weekly summary");
		btnWeekly = new Button(this, SWT.RADIO);
		btnWeekly.setSelection(summaryTimePeriod == TimePeriod.WEEKLY);
		btnWeekly.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				summaryTimePeriod = btnWeekly.getSelection() ? TimePeriod.WEEKLY: TimePeriod.DAILY;
			}
		});
	}


	private void createMaxHrsPerTimeSpanGroup() {
		Label lblHrsDay = new Label(this, SWT.NONE);
		lblHrsDay.setText("Maximum hours per day");

		spinnerHrsDay = new Spinner(this, SWT.BORDER);
		spinnerHrsDay.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		spinnerHrsDay.setMinimum(1);
		spinnerHrsDay.setMaximum(12);
		spinnerHrsDay.setIncrement(1);
		spinnerHrsDay.setSelection(maxDailyHours);
		spinnerHrsDay.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				maxDailyHours = spinnerHrsDay.getSelection();
			}
		});

		Label lblHrsWeekly = new Label(this, SWT.NONE);
		lblHrsWeekly.setText("Maximum hours per week");

		spinnerHrsWeekly = new Spinner(this, SWT.BORDER);
		spinnerHrsWeekly.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		spinnerHrsWeekly.setMinimum(1);
		spinnerHrsWeekly.setMaximum(80);
		spinnerHrsWeekly.setIncrement(1);
		spinnerHrsWeekly.setSelection(maxWeeklyHours);
		spinnerHrsWeekly.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				maxWeeklyHours = spinnerHrsWeekly.getSelection();
			}
		});
	}

	@Override
	public void updatePreferenceFields() {
		Display.getCurrent().asyncExec(new Runnable() {
			@Override
			public void run() {
				updatePreferenceFieldsInternal();
			}
		});
	}

	private void updatePreferenceFieldsInternal() {
		summaryTimePeriod = prefs.getWorkItemSummaryTimePeriod();
		maxDailyHours = prefs.getMaximumNumberHoursPerDay();
		maxWeeklyHours = prefs.getMaximumNumberOfHoursPerWeek();
		if (notNullNotDisposed(btnDaily)
				&& notNullNotDisposed(btnWeekly)
				&& notNullNotDisposed(spinnerHrsDay)
				&& notNullNotDisposed(spinnerHrsWeekly)) {
			btnDaily.setSelection(summaryTimePeriod == TimePeriod.DAILY);
			btnWeekly.setSelection(summaryTimePeriod == TimePeriod.WEEKLY);
			spinnerHrsDay.setSelection(maxDailyHours);
			spinnerHrsWeekly.setSelection(maxWeeklyHours);
		}
	}

	private boolean notNullNotDisposed(Control c) {
		return c != null && !c.isDisposed();
	}

	@Override
	public void flushPreferences() {
		prefs.setWorkItemSummaryTimePeriod(summaryTimePeriod);
		prefs.setMaximumNumberHoursPerDay(maxDailyHours);
		prefs.setMaximumNumberHoursPerWeek(maxWeeklyHours);
	}
}
