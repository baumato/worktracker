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
package org.tobbaumann.wt.ui.views.date;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.tobbaumann.wt.ui.preferences.WorkTrackerPreferences;
import org.tobbaumann.wt.ui.views.PreferencesComposite;

import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.common.io.Resources;

public class DatesViewSettings extends PreferencesComposite {

	/**
	 *
	 * @author tobbaumann
	 *
	 */
	private static class ComboDateFormat {
		static ComboDateFormat SHORT = new ComboDateFormat("short", DateFormat.SHORT);
		static ComboDateFormat LONG = new ComboDateFormat("long", DateFormat.LONG);
		static ComboDateFormat CUSTOM = new ComboDateFormat("custom", -1);

		private final String displayNamePrefix;
		private final int dateFormatStyle;
		private final WorkTrackerPreferences prefs = new WorkTrackerPreferences();

		private ComboDateFormat(String displayNamePrefix, int dateFormatStyle) {
			this.displayNamePrefix = displayNamePrefix;
			this.dateFormatStyle = dateFormatStyle;
		}

		static ComboDateFormat instance(int dateFormatStyle) {
			switch (dateFormatStyle) {
				case DateFormat.SHORT:
					return SHORT;
				case DateFormat.LONG:
					return LONG;
				default:
					return CUSTOM;
			}
		}

		static ComboDateFormat instance(DateFormat df) {
			if (df.equals(DateFormat.getDateInstance(DateFormat.SHORT))) {
				return SHORT;
			}
			if (df.equals(DateFormat.getDateInstance(DateFormat.LONG))) {
				return LONG;
			}
			return CUSTOM;
		}

		String getDisplayName() {
			return displayNamePrefix + " (" + getDateFormat().format(new Date()) + ")";
		}

		private DateFormat getDateFormat() {
			return dateFormatStyle == -1 ? new SimpleDateFormat(prefs.getDatesViewDateFormatPattern()) : DateFormat.getDateInstance(dateFormatStyle);
		}

		public boolean isCustomDateFormat() {
			return this == CUSTOM;
		}
	}

	private ComboViewer cmbDateFormat;
	private ControlDecoration dateFormatControlDecoration;
	private Text txtCustomDateFormat;
	private Button btnShowWeekdays;

	private int dateFormatStyle;
	private String dateFormatPattern;
	private boolean showWeekdays;

	public DatesViewSettings(Composite parent, WorkTrackerPreferences prefs) {
		super(parent, prefs);
		createControl();
		updatePreferenceFields();
	}

	@Override
	public void updatePreferenceFields() {
		this.dateFormatStyle = ComboDateFormat.instance(prefs.getDatesViewDateFormat()).dateFormatStyle;
		this.dateFormatPattern = prefs.getDatesViewDateFormatPattern();
		this.showWeekdays = prefs.getDatesViewShowWeekdays();
		cmbDateFormat.setSelection(new StructuredSelection(ComboDateFormat.instance(dateFormatStyle)));
		txtCustomDateFormat.setText(dateFormatPattern);
		txtCustomDateFormat.setEnabled(getSelectedDateFormat().isCustomDateFormat());
		btnShowWeekdays.setSelection(showWeekdays);
	}

	private void createControl() {
		setLayout(new GridLayout(2, false));

		createLabel(SWT.LEFT, prefs.getDatesViewDateFormatDisplayName(), "How should the dates be shown?");
		cmbDateFormat = new ComboViewer(this, SWT.READ_ONLY);
		cmbDateFormat.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		cmbDateFormat.setContentProvider(new ArrayContentProvider());
		cmbDateFormat.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((ComboDateFormat)element).getDisplayName();
			}
		});
		cmbDateFormat.setInput(Arrays.asList(ComboDateFormat.SHORT, ComboDateFormat.LONG, ComboDateFormat.CUSTOM));
		cmbDateFormat.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				handleDateFormatSelection();
			}
		});

		createLabel(SWT.LEFT, prefs.getDatesViewDateFormatPatternDisplayName(), "Define the custom date format pattern here.");
		txtCustomDateFormat = new Text(this, SWT.BORDER | SWT.SINGLE | SWT.LEFT);
		txtCustomDateFormat.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		txtCustomDateFormat.setMessage("Enter custom date format here.");
		txtCustomDateFormat.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent me) {
				handleCustomDateFormatTextChanged();
			}
		});
		applyToolTipToCustomDateFormat();

		createLabel(SWT.LEFT, prefs.getDatesViewShowWeekdaysDisplayName(), "Should the day of week shown next to date?");
		btnShowWeekdays = new Button(this, SWT.CHECK);
		btnShowWeekdays.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnShowWeekdays.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				showWeekdays = btnShowWeekdays.getSelection();
			}
		});
	}

	private void createLabel(int style, String text, String toolTipText) {
		Label label = new Label(this, style);
		label.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		label.setText(text);
		label.setToolTipText(toolTipText);
	}

	private void handleDateFormatSelection() {
		ComboDateFormat selectedDateFormat = getSelectedDateFormat();
		txtCustomDateFormat.setEnabled(selectedDateFormat.isCustomDateFormat());
		dateFormatStyle = selectedDateFormat.dateFormatStyle;
	}

	private ComboDateFormat getSelectedDateFormat() {
		IStructuredSelection s = (IStructuredSelection) cmbDateFormat.getSelection();
		return (ComboDateFormat) s.getFirstElement();
	}

	private void applyToolTipToCustomDateFormat() {
		try {
			String toolTipContent = Resources.toString(
					Resources.getResource(DatesViewSettings.class, "dateformat.html"),
					Charsets.UTF_8);
			HtmlToolTip.apply(txtCustomDateFormat, toolTipContent, 480, 400);
		} catch (IOException e) {
			Throwables.propagate(e);
		}
	}

	private void handleCustomDateFormatTextChanged() {
		if (txtCustomDateFormat.getText().trim().isEmpty()) {
			String msgPattern = "if '%s' is set to '%s' then the '%s' should not be empty.";
			String msg = String.format(msgPattern,
				prefs.getDatesViewDateFormatDisplayName(),
				ComboDateFormat.CUSTOM.displayNamePrefix,
				prefs.getDatesViewDateFormatPatternDisplayName());
			setErrorMessage(msg);
			return;
		}
		try {
			new SimpleDateFormat(txtCustomDateFormat.getText());
			setErrorMessage(null);
			dateFormatPattern = txtCustomDateFormat.getText();
		} catch (IllegalArgumentException e) {
			setErrorMessage("Illegal date format pattern '" + txtCustomDateFormat.getText() + "'. Please refert tooltip for details.");
		}
	}

	private void setErrorMessage(String msg) {
		if (msg == null && dateFormatControlDecoration == null) {
			return;
		}
		if (dateFormatControlDecoration == null) {
			dateFormatControlDecoration = new ControlDecoration(txtCustomDateFormat, SWT.LEFT | SWT.TOP);
			FieldDecoration fieldDecoration = FieldDecorationRegistry.getDefault()
					.getFieldDecoration(FieldDecorationRegistry.DEC_WARNING);
			dateFormatControlDecoration.setImage(fieldDecoration.getImage());
			dateFormatControlDecoration.setShowHover(true);
			dateFormatControlDecoration.hide();
		}

		if (msg == null) {
			dateFormatControlDecoration.hide();
		} else {
			dateFormatControlDecoration.setDescriptionText(msg);
			dateFormatControlDecoration.show();
		}
	}

	@Override
	public void flushPreferences() {
		prefs.setDatesViewDateFormat(dateFormatStyle);
		prefs.setDatesViewDateFormatPattern(dateFormatPattern);
		prefs.setDatesViewShowWeekdays(showWeekdays);
	}
}
