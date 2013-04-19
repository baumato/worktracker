/*******************************************************************************
 * Copyright (c) 2013 Tobias Baumann. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Tobias Baumann - initial API and implementation
 ******************************************************************************/
package org.tobbaumann.wt.ui.preferences;

import java.util.concurrent.TimeUnit;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.tobbaumann.wt.domain.util.TimeSpanHelper;

public class PreferencesDialog extends TitleAreaDialog {

	private static final String DIALOG_TITLE = "Preferences";

	private final WorkTrackerPreferences prefs;
	private Label lblReminder;
	private Spinner spinnerReminderFreq;
	private Combo cmbReminderFreqTimeUnit;
	private Button btnUseReminder;
	private Spinner spinnerStatusLineFreq;
	private Combo cmbStatusLineTimeUnit;

	/**
	 * Constructor
	 *
	 * @param parentShell
	 * @param prefs
	 */
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
		container.setLayout(new GridLayout(1, false));
		GridData gd_container = new GridData(GridData.FILL_BOTH);
		gd_container.heightHint = 226;
		container.setLayoutData(gd_container);

		Group grpReminder = new Group(container, SWT.NONE);
		grpReminder.setLayout(new GridLayout(3, false));
		grpReminder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpReminder.setText("Reminder");

		Label lblUseReminder = new Label(grpReminder, SWT.NONE);
		lblUseReminder.setText("Use Reminder");

		btnUseReminder = new Button(grpReminder, SWT.CHECK);
		btnUseReminder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean useReminder = btnUseReminder.getSelection();
				lblReminder.setEnabled(useReminder);
				spinnerReminderFreq.setEnabled(useReminder);
				cmbReminderFreqTimeUnit.setEnabled(useReminder);
			}
		});
		btnUseReminder.setSelection(prefs.getUseReminder());
		new Label(grpReminder, SWT.NONE);

		lblReminder = new Label(grpReminder, SWT.NONE);
		lblReminder.setText("Remember me every");

		spinnerReminderFreq = new Spinner(grpReminder, SWT.BORDER);
		spinnerReminderFreq.setPageIncrement(5);
		spinnerReminderFreq.setMaximum(480);
		spinnerReminderFreq.setMinimum(1);
		Duration duration = Duration.newInstance(prefs.getRemindFrequencyInMillis());
		spinnerReminderFreq.setSelection(duration.number);

		cmbReminderFreqTimeUnit = new Combo(grpReminder, SWT.READ_ONLY);
		cmbReminderFreqTimeUnit.setItems(new String[] {
				TimeUnit.SECONDS.name().toLowerCase(),
				TimeUnit.MINUTES.name().toLowerCase(),
				TimeUnit.HOURS.name().toLowerCase() });
		cmbReminderFreqTimeUnit.setText(duration.timeUnit.name().toLowerCase());

		Group grpStatusLine = new Group(container, SWT.NONE);
		grpStatusLine.setLayout(new GridLayout(3, false));
		grpStatusLine.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpStatusLine.setText("Status Line");

		Label lblStatusLine = new Label(grpStatusLine, SWT.NONE);
		lblStatusLine.setText("Update Status Line every");

		spinnerStatusLineFreq = new Spinner(grpStatusLine, SWT.BORDER);
		spinnerStatusLineFreq.setPageIncrement(5);
		duration = Duration.newInstance(prefs.getStatusLineUpdateFrequencyInMillis());
		spinnerStatusLineFreq.setSelection(duration.number);

		cmbStatusLineTimeUnit = new Combo(grpStatusLine, SWT.READ_ONLY);
		cmbStatusLineTimeUnit.setItems(new String[] {
				TimeUnit.SECONDS.name().toLowerCase(),
				TimeUnit.MINUTES.name().toLowerCase(),
				TimeUnit.HOURS.name().toLowerCase() });
		cmbStatusLineTimeUnit.setText(duration.timeUnit.name().toLowerCase());

		return area;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(DIALOG_TITLE);
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
		return super.getInitialSize();
	}


	private static final class Duration {

		final int number;
		final TimeUnit timeUnit;

		private Duration(int number, TimeUnit timeUnit) {
			this.number = number;
			this.timeUnit = timeUnit;
		}

		public static Duration newInstance(long millis) {
			TimeSpanHelper h = new TimeSpanHelper(millis);
			if (h.inHours() > 0) {
				return new Duration(h.inHours(), TimeUnit.HOURS);
			}
			if (h.inMinutes() > 0) {
				return new Duration(h.inMinutes(), TimeUnit.MINUTES);
			}
			if (h.inSeconds() > 0) {
				return new Duration(h.inSeconds(), TimeUnit.SECONDS);
			}
			return new Duration((int) millis, TimeUnit.MILLISECONDS);
		}

		@Override
		public String toString() {
			return number + " " + timeUnit.name().toLowerCase();
		}
	}
}
