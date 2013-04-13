/*******************************************************************************
 * Copyright (c) 2013 tobba_000.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     tobba_000 - initial API and implementation
 ******************************************************************************/
package org.tobbaumann.wt.ui.views.date;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.inject.Inject;

import org.eclipse.e4.core.services.log.Logger;
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

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

class DatesViewSettings extends Composite {

	private @Inject Logger logger;

	private ComboViewer cmbDateFormat;
	private ControlDecoration dateFormatControlDecoration;
	private Text txtCustomDateFormat;

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

		private ComboDateFormat(String displayNamePrefix, int dateFormatStyle) {
			this.displayNamePrefix = displayNamePrefix;
			this.dateFormatStyle = dateFormatStyle;
		}

		public static ComboDateFormat instance(int dateFormatStyle) {
			 switch (dateFormatStyle) {
			case DateFormat.SHORT:
				return SHORT;
			case DateFormat.LONG:
				return LONG;
			case -1:
				return CUSTOM;
			default:
				throw new IllegalArgumentException("Invalid dateFormatStyle given: " + dateFormatStyle);
			}
		}

		public String getDisplayName() {
			return displayNamePrefix + " (" + getDateFormat().format(new Date()) + ")";
		}

		private DateFormat getDateFormat() {
			return dateFormatStyle == -1 ? new SimpleDateFormat(DatesViewPreference.getCurrentDateFormatPattern()) : DateFormat.getDateInstance(dateFormatStyle);
		}

		public boolean isCustomDateFormat() {
			return this == CUSTOM;
		}

	}

	DatesViewSettings(Composite parent) {
		super(parent, SWT.NONE);
		createControl();
	}

	private void createControl() {
		setLayout(new GridLayout(2, false));

		DatesViewPreference pref = DatesViewPreference.DATE_FORMAT;
		createLabel(SWT.LEFT, pref.getName(), "How should the dates be shown?");
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
		cmbDateFormat.setSelection(new StructuredSelection(ComboDateFormat.instance(Integer.valueOf(pref.getValue()))));
		cmbDateFormat.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				handleDateFormatSelection();
			}
		});

		pref = DatesViewPreference.DATE_FORMAT_PATTERN;
		createLabel(SWT.LEFT, pref.getName(), "Define the custom date format pattern here.");
		txtCustomDateFormat = new Text(this, SWT.BORDER | SWT.SINGLE | SWT.LEFT);
		txtCustomDateFormat.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		txtCustomDateFormat.setMessage("Enter custom date format here.");
		txtCustomDateFormat.setText(pref.getValue());
		txtCustomDateFormat.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent me) {
				handleCustomDateFormatTextChanged();
			}
		});
		txtCustomDateFormat.setEnabled(getSelectedDateFormat().isCustomDateFormat());
		applyToolTipToCustomDateFormat();

		pref = DatesViewPreference.SHOW_WEEKDAYS;
		createLabel(SWT.LEFT, pref.getName(), "Should the day of week shown next to date?");
		final Button btnShowWeekdays = new Button(this, SWT.CHECK);
		btnShowWeekdays.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnShowWeekdays.setSelection(Boolean.valueOf(pref.getValue()));
		btnShowWeekdays.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DatesViewPreference pref = DatesViewPreference.SHOW_WEEKDAYS;
				pref.putValue(String.valueOf(btnShowWeekdays.getSelection()));
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
		DatesViewPreference.DATE_FORMAT.putValue(String.valueOf(selectedDateFormat.dateFormatStyle));
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
			logger.warn(e, "Was not able to apply tooltip.");
		}
	}

	private void handleCustomDateFormatTextChanged() {
		if (txtCustomDateFormat.getText().trim().isEmpty()) {
			String msgPattern = "if '%s' is set to '%s' then the '%s' should not be empty.";
			String msg = String.format(msgPattern,
					DatesViewPreference.DATE_FORMAT.getName(),
					ComboDateFormat.CUSTOM.displayNamePrefix,
					DatesViewPreference.DATE_FORMAT_PATTERN.getName());
			setErrorMessage(msg);
			return;
		}
		try {
			new SimpleDateFormat(txtCustomDateFormat.getText());
			setErrorMessage(null);
			DatesViewPreference.DATE_FORMAT_PATTERN.putValue(txtCustomDateFormat.getText());
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
}
