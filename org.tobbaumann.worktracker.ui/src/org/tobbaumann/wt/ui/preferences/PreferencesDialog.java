/*******************************************************************************
 * Copyright (c) 2013 Tobias Baumann. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Tobias Baumann - initial API and implementation
 ******************************************************************************/
package org.tobbaumann.wt.ui.preferences;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.tobbaumann.wt.domain.util.TimeSpanHelper;
import org.tobbaumann.wt.ui.views.date.DatesViewSettings;
import org.tobbaumann.wt.ui.views.startwibutton.StartWorkItemWithButtonViewSettings;
import org.tobbaumann.wt.ui.views.wisummary.WorkItemSummaryViewSettings;

@Creatable
public class PreferencesDialog extends TitleAreaDialog {

	private static final String DIALOG_TITLE = "Preferences";

	private final WorkTrackerPreferences prefs;
	private Label lblReminder;
	private Spinner spinnerReminderFreq;
	private Combo cmbReminderFreqTimeUnit;
	private Button btnUseReminder;
	private Spinner spinnerStatusLineFreq;
	private Combo cmbStatusLineTimeUnit;
	private DatesViewSettings datesViewSettings;
	private StartWorkItemWithButtonViewSettings startWorkItemWithButtonViewSettings;
	private WorkItemSummaryViewSettings summarySettings;

	@Inject
	@Preference(value = WorkTrackerPreferences.USE_REMINDER)
	private boolean useReminder;

	@Inject
	@Preference(value = WorkTrackerPreferences.REMIND_FREQUENCY)
	private long remindFrequencyInMillis;

	@Inject
	@Preference(value = WorkTrackerPreferences.STATUS_LINE_UPDATE_FREQUENCY)
	private long statusLineUpdateFreqInMillis;

	/**
	 * Constructor
	 *
	 * @param parentShell
	 * @param prefs
	 */
	@Inject
	public PreferencesDialog(Shell parentShell, WorkTrackerPreferences prefs) {
		super(parentShell);
		this.prefs = prefs;
		setHelpAvailable(false);
	}

	/**
	 * Create contents of the dialog.
	 *
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle(DIALOG_TITLE);
		setMessage("Change the general preferences here.");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayoutFactory.fillDefaults().applyTo(container);

		createReminderPart(container);
		createStatusLinePart(container);
		createDatesViewPart(container);
		createStartWorkItemWithButtonViewPart(container);
		createWorkItemSummaryPart(container);
		return area;
	}

	private void createReminderPart(Composite parent) {
		createHeadline(parent, "Reminder");

		Composite container = new Composite(parent, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		container.setLayout(new GridLayout(3, false));

		Label lblUseReminder = new Label(container, SWT.NONE);
		lblUseReminder.setText("Use Reminder");
		btnUseReminder = new Button(container, SWT.CHECK);
		btnUseReminder.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1));
		btnUseReminder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean useReminder = btnUseReminder.getSelection();
				lblReminder.setEnabled(useReminder);
				spinnerReminderFreq.setEnabled(useReminder);
				cmbReminderFreqTimeUnit.setEnabled(useReminder);
			}
		});
		btnUseReminder.setSelection(useReminder);

		lblReminder = new Label(container, SWT.NONE);
		lblReminder.setText("Remember me every");
		spinnerReminderFreq = new Spinner(container, SWT.BORDER);
		spinnerReminderFreq.setPageIncrement(5);
		spinnerReminderFreq.setMaximum(480);
		spinnerReminderFreq.setMinimum(1);
		Duration duration = Duration.newInstance(remindFrequencyInMillis);
		spinnerReminderFreq.setSelection(duration.number);

		cmbReminderFreqTimeUnit = new Combo(container, SWT.READ_ONLY);
		cmbReminderFreqTimeUnit.setItems(new String[] {
				TimeUnit.SECONDS.name().toLowerCase(),
				TimeUnit.MINUTES.name().toLowerCase(),
				TimeUnit.HOURS.name().toLowerCase() });
		cmbReminderFreqTimeUnit.setText(duration.timeUnit.name().toLowerCase());
	}

	private void createStatusLinePart(Composite parent) {
		createHeadline(parent, "Status Line");

		Composite container = new Composite(parent, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		container.setLayout(new GridLayout(3, false));

		Duration duration;
		Label lblStatusLine = new Label(container, SWT.NONE);
		lblStatusLine.setText("Update Status Line every");

		spinnerStatusLineFreq = new Spinner(container, SWT.BORDER);
		spinnerStatusLineFreq.setPageIncrement(5);
		duration = Duration.newInstance(statusLineUpdateFreqInMillis);
		spinnerStatusLineFreq.setSelection(duration.number);

		cmbStatusLineTimeUnit = new Combo(container, SWT.READ_ONLY);
		cmbStatusLineTimeUnit.setItems(new String[] {
				TimeUnit.SECONDS.name().toLowerCase(),
				TimeUnit.MINUTES.name().toLowerCase(),
				TimeUnit.HOURS.name().toLowerCase() });
		cmbStatusLineTimeUnit.setText(duration.timeUnit.name().toLowerCase());
	}

	private void createDatesViewPart(Composite container) {
		createHeadline(container, "Dates View");
		datesViewSettings = new DatesViewSettings(container, prefs);
		datesViewSettings.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
	}

	private void createStartWorkItemWithButtonViewPart(Composite container) {
		createHeadline(container, "Start Work Item With Button View");
		startWorkItemWithButtonViewSettings = new StartWorkItemWithButtonViewSettings(container, prefs);
		startWorkItemWithButtonViewSettings.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
	}

	private void createWorkItemSummaryPart(Composite container) {
		createHeadline(container, "Work Item Summary Settings");
		summarySettings = new WorkItemSummaryViewSettings(container, prefs);
		summarySettings.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
	}

	private void createHeadline(Composite area, String headline) {
		Label topSpace = new Label(area, SWT.NONE);
		GridDataFactory.fillDefaults().applyTo(topSpace);
		Label title = new Label(area, SWT.NONE);
		title.setData("org.eclipse.e4.ui.css.id", "PreferencesDialogHeadline");
		title.setText("  " + headline);
		GridDataFactory.fillDefaults().applyTo(title);
		Label sep = new Label(area, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridDataFactory.swtDefaults().applyTo(sep);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(DIALOG_TITLE);
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	/**
	 * Create contents of the button bar.
	 *
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected void okPressed() {
		prefs.setUseReminder(btnUseReminder.getSelection());
		int nr = spinnerReminderFreq.getSelection();
		TimeUnit tu = getTimeUnit(cmbReminderFreqTimeUnit.getText());
		prefs.setRemindFrequencyInMillis(TimeUnit.MILLISECONDS.convert(nr, tu));

		nr = spinnerStatusLineFreq.getSelection();
		tu = getTimeUnit(cmbStatusLineTimeUnit.getText());
		prefs.setStatusLineUpdateFrequencyInMillis(TimeUnit.MILLISECONDS.convert(nr, tu));

		datesViewSettings.flushPreferences();
		startWorkItemWithButtonViewSettings.flushPreferences();
		summarySettings.flushPreferences();

		super.okPressed();
	}

	TimeUnit getTimeUnit(String timeUnit) {
		for (TimeUnit t : TimeUnit.values()) {
			if (t.name().equalsIgnoreCase(timeUnit)) {
				return t;
			}
		}
		throw new IllegalArgumentException(timeUnit + " is not a valid time unit.");
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		Point size = super.getInitialSize();
		return new Point(Math.max(480, size.x), Math.max(600, size.y));
	}


	/**
	 *
	 * @author tobbaumann
	 *
	 */
	private static final class Duration {

		final int number;
		final TimeUnit timeUnit;

		private Duration(int number, TimeUnit timeUnit) {
			this.number = number;
			this.timeUnit = timeUnit;
		}

		public static Duration newInstance(long millis) {
			TimeSpanHelper h = new TimeSpanHelper(millis);
			if (h.getSeconds() > 0) {
				return new Duration(h.inSeconds(), TimeUnit.SECONDS);
			}
			if (h.getMinutes() > 0) {
				return new Duration(h.inMinutes(), TimeUnit.MINUTES);
			}
			if (h.getHours() > 0) {
				return new Duration(h.inHours(), TimeUnit.HOURS);
			}
			return new Duration((int) millis, TimeUnit.MILLISECONDS);
		}

		@Override
		public String toString() {
			return number + " " + timeUnit.name().toLowerCase();
		}
	}
}
