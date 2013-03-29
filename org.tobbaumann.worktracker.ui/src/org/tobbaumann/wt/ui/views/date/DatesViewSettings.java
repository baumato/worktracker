package org.tobbaumann.wt.ui.views.date;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.inject.Inject;

import org.eclipse.e4.core.services.log.Logger;
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
	private Text txtCustomDateFormat;


	DatesViewSettings(Composite parent) {
		super(parent, SWT.NONE);
		createControl();
	}

	private void createControl() {
		setLayout(new GridLayout(2, false));

		DatesViewPreference pref = DatesViewPreference.DATE_FORMAT;
		createLabel(SWT.LEFT, pref.getName(), "How should the dates be shown?");
		cmbDateFormat = new ComboViewer(this, SWT.READ_ONLY);
		cmbDateFormat.getCombo().setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
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
		txtCustomDateFormat.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		txtCustomDateFormat.setMessage("Enter custom date format here.");
		txtCustomDateFormat.setText(pref.getValue());
		txtCustomDateFormat.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent me) {
				try {
					new SimpleDateFormat(txtCustomDateFormat.getText());
					setErrorMessage(null);
					DatesViewPreference.DATE_FORMAT_PATTERN.putValue(txtCustomDateFormat.getText());
				} catch (IllegalArgumentException e) {
					setErrorMessage("Illegal date format pattern: " + txtCustomDateFormat.getText());
				}
			}
		});
		txtCustomDateFormat.setEnabled(getSelectedDateFormat().isCustomDateFormat());
		try {
			String toolTipContent = Resources.toString(
					Resources.getResource(DatesViewSettings.class, "dateformat.html"),
					Charsets.UTF_8);
			new HtmlToolTip(txtCustomDateFormat, toolTipContent, 480, 400);
		} catch (IOException e) {
			logger.warn(e, "Was not able to apply tooltip.");
		}


		pref = DatesViewPreference.SHOW_WEEKDAYS;
		createLabel(SWT.LEFT, pref.getName(), "Should the day of week shown next to date?");
		final Button btnShowWeekdays = new Button(this, SWT.CHECK);
		btnShowWeekdays.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
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

	private void setErrorMessage(String msg) {
		// TODO
		System.out.println(msg);
	}


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
}
