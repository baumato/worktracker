/*******************************************************************************
 * Copyright (c) 2013 tobba_000. All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: tobba_000 - initial API and implementation
 ******************************************************************************/
package org.tobbaumann.wt.ui.preferences;

import static com.google.common.collect.Lists.newArrayList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.osgi.service.prefs.BackingStoreException;

import com.google.common.base.CaseFormat;

@Creatable
public class WorkTrackerPreferences {

	private static final String REMINDER_NODE_NAME = "worktracker.preferences.reminder";
	private static final String USE_REMINDER = "useReminder";
	private static final Boolean USE_REMINDER_DEFAULT = true;
	private static final String REMIND_FREQUENCY = "remindFrequency";
	private static final long REMIND_FREQUENCY_DEFAULT = TimeUnit.MILLISECONDS.convert(15, TimeUnit.MINUTES);

	private static final String STATUS_LINE_NODE_NAME = "worktracker.preferences.statusLine";
	private static final String STATUS_LINE_UPDATE_FREQUENCY = "updateFrequency";
	private static final long STATUS_LINE_UPDATE_FREQUENCY_DEFAULT = 1000;

	private static final String DATES_VIEW_NODE_NAME = "worktracker.preferences.datesView";
	private static final String DATES_VIEW_DATE_FORMAT = "DATE_FORMAT";
	private static final String DATES_VIEW_DATE_FORMAT_DISPLAY_NAME = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, DATES_VIEW_DATE_FORMAT);
	private static final int DATES_VIEW_DATE_FORMAT_DEFAULT = DateFormat.SHORT;
	private static final String DATES_VIEW_DATE_FORMAT_PATTERN = "DATE_FORMAT_PATTERN";
	private static final String DATES_VIEW_DATE_FORMAT_PATTERN_DISPLAY_NAME = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, DATES_VIEW_DATE_FORMAT_PATTERN);
	private static final String DATES_VIEW_DATE_FORMAT_PATTERN_DEFAULT = "yyyy-MM-dd";
	private static final String DATES_VIEW_SHOW_WEEKDAYS = "SHOW_WEEKDAYS";
	private static final String DATES_VIEW_SHOW_WEEKDAYS_DISPLAY_NAME = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, DATES_VIEW_SHOW_WEEKDAYS);
	private static final boolean DATES_VIEW_SHOW_WEEKDAYS_DEFAULT = true;


	private static final String STARTWI_VIEW_NODE_NAME = "worktracker.preferences.startWorkItemsWithButtonView";
	private static final String STARTWI_VIEW_NUMBER_OF_BUTTONS = "NUMBER_OF_BUTTONS";
	private static final String STARTWI_VIEW_NUMBER_OF_BUTTONS_DISPLAY_NAME = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, STARTWI_VIEW_NUMBER_OF_BUTTONS);
	private static final int STARTWI_VIEW_NUMBER_OF_BUTTONS_DEFAULT_VALUE = 6;
	private static final int STARTWI_VIEW_NUMBER_OF_BUTTONS_MAXIMUM_VALUE = 20;
	private static final String STARTWI_VIEW_NUMBER_OF_BUTTON_COLUMNS = "NUMBER_OF_BUTTON_COLUMNS";
	private static final String STARTWI_VIEW_NUMBER_OF_BUTTON_COLUMNS_DISPLAY_NAME = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, STARTWI_VIEW_NUMBER_OF_BUTTON_COLUMNS);
	private static final int STARTWI_VIEW_NUMBER_OF_BUTTON_COLUMNS_DEFAULT_VALUE = 1;
	private static final int STARTWI_VIEW_NUMBER_OF_BUTTON_COLUMNS_MAXIMUM_VALUE = 6;
	private static final String STARTWI_VIEW_BUTTON_LABELS = "BUTTON_LABELS";

	/*
	 * Reminder
	 */

	public long getRemindFrequencyInMillis() {
		return ConfigurationScope.INSTANCE.getNode(REMINDER_NODE_NAME).getLong(
			REMIND_FREQUENCY,
			REMIND_FREQUENCY_DEFAULT);
	}

	public void setRemindFrequencyInMillis(long millis) {
		IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode(REMINDER_NODE_NAME);
		node.putLong(REMIND_FREQUENCY, millis);
		flushNode(node);
	}

	public boolean getUseReminder() {
		return ConfigurationScope.INSTANCE.getNode(REMINDER_NODE_NAME).getBoolean(
			USE_REMINDER,
			USE_REMINDER_DEFAULT);
	}

	public void setUseReminder(boolean useReminder) {
		IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode(REMINDER_NODE_NAME);
		node.putBoolean(USE_REMINDER, useReminder);
		flushNode(node);
	}


	/*
	 * StatusLine
	 */

	public long getStatusLineUpdateFrequencyInMillis() {
		return ConfigurationScope.INSTANCE.getNode(STATUS_LINE_NODE_NAME).getLong(
			STATUS_LINE_UPDATE_FREQUENCY,
			STATUS_LINE_UPDATE_FREQUENCY_DEFAULT);
	}

	public void setStatusLineUpdateFrequencyInMillis(long millis) {
		IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode(STATUS_LINE_NODE_NAME);
		node.putLong(STATUS_LINE_UPDATE_FREQUENCY, millis);
		flushNode(node);
	}


	/*
	 * DatesView
	 */

	public String getDatesViewDateFormatDisplayName() {
		return DATES_VIEW_DATE_FORMAT_DISPLAY_NAME;
	}

	// TODO evtl. zu int (dateFormatStyle ändern)
	public DateFormat getDatesViewDateFormat() {
		IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode(DATES_VIEW_NODE_NAME);
		Integer dfStyle = Integer.valueOf(node.getInt(DATES_VIEW_DATE_FORMAT, DATES_VIEW_DATE_FORMAT_DEFAULT));
		return dfStyle == -1
				? new SimpleDateFormat(getDatesViewDateFormatPattern())
				: DateFormat.getDateInstance(dfStyle);
	}

	public void setDatesViewDateFormat(int dfStyle) {
		IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode(DATES_VIEW_NODE_NAME);
		node.putInt(DATES_VIEW_DATE_FORMAT, dfStyle);
		flushNode(node);
	}

	public String getDatesViewDateFormatPatternDisplayName() {
		return DATES_VIEW_DATE_FORMAT_PATTERN_DISPLAY_NAME;
	}

	public String getDatesViewDateFormatPattern() {
		IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode(DATES_VIEW_NODE_NAME);
		return node.get(DATES_VIEW_DATE_FORMAT_PATTERN, DATES_VIEW_DATE_FORMAT_PATTERN_DEFAULT);
	}

	public void setDatesViewDateFormatPattern(String pattern) {
		IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode(DATES_VIEW_NODE_NAME);
		node.put(DATES_VIEW_DATE_FORMAT_PATTERN, pattern);
		flushNode(node);
	}

	public String getDatesViewShowWeekdaysDisplayName() {
		return DATES_VIEW_SHOW_WEEKDAYS_DISPLAY_NAME;
	}

	public boolean getDatesViewShowWeekdays() {
		IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode(DATES_VIEW_NODE_NAME);
		return node.getBoolean(DATES_VIEW_SHOW_WEEKDAYS, DATES_VIEW_SHOW_WEEKDAYS_DEFAULT);
	}

	public void setDatesViewShowWeekdays(boolean showWeekdays) {
		IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode(DATES_VIEW_NODE_NAME);
		node.putBoolean(DATES_VIEW_SHOW_WEEKDAYS, showWeekdays);
		flushNode(node);
	}


	/*
	 * StartWorkItemWithButtonView
	 */

	public int getNumberOfButtons() {
		IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode(STARTWI_VIEW_NODE_NAME);
		return node.getInt(STARTWI_VIEW_NUMBER_OF_BUTTONS, STARTWI_VIEW_NUMBER_OF_BUTTONS_DEFAULT_VALUE);
	}

	public String getNumberOfButtonsDisplayName() {
		return STARTWI_VIEW_NUMBER_OF_BUTTONS_DISPLAY_NAME;
	}

	public void setNumberOfButtons(int numberOfButtons) {
		IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode(STARTWI_VIEW_NODE_NAME);
		node.putInt(STARTWI_VIEW_NUMBER_OF_BUTTONS, numberOfButtons);
		flushNode(node);
	}

	public int getMaximumNumberOfButtons() {
		return STARTWI_VIEW_NUMBER_OF_BUTTONS_MAXIMUM_VALUE;
	}

	public int getNumberOfButtonColumns() {
		IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode(STARTWI_VIEW_NODE_NAME);
		return node.getInt(STARTWI_VIEW_NUMBER_OF_BUTTON_COLUMNS, STARTWI_VIEW_NUMBER_OF_BUTTON_COLUMNS_DEFAULT_VALUE);
	}

	public String getNumberOfButtonColumnsDisplayName() {
		return STARTWI_VIEW_NUMBER_OF_BUTTON_COLUMNS_DISPLAY_NAME;
	}

	public void setNumberOfButtonColumns(int numberOfButtonColumns) {
		IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode(STARTWI_VIEW_NODE_NAME);
		node.putInt(STARTWI_VIEW_NUMBER_OF_BUTTON_COLUMNS, numberOfButtonColumns);
		flushNode(node);
	}

	public int getMaximumNumberOfButtonColumns() {
		return STARTWI_VIEW_NUMBER_OF_BUTTON_COLUMNS_MAXIMUM_VALUE;
	}

	public boolean customWorkItemStartButtonLabelsAvailable() {
		IEclipsePreferences node = getButtonLabelsNode();
		try {
			return node != null && node.keys().length > 0;
		} catch (BackingStoreException e) {
			throw new RuntimeException("Error during reading preferences node '" + node.name() + "'.", e);
		}
	}

	private IEclipsePreferences getButtonLabelsNode() {
		String nodePath = STARTWI_VIEW_NODE_NAME + "/" + STARTWI_VIEW_BUTTON_LABELS;
		return ConfigurationScope.INSTANCE.getNode(nodePath);
	}

	public List<String> getWorkItemStartButtonLabels() {
		if (!customWorkItemStartButtonLabelsAvailable()) {
			return Collections.emptyList();
		}
		IEclipsePreferences node = getButtonLabelsNode();
		try {
			List<String> activityNames = newArrayList();
			for (String key : node.keys()) {
				activityNames.add(node.get(key, ""));
			}
			return activityNames;
		} catch (BackingStoreException e) {
			throw new RuntimeException("Error during reading preferences node '" + node.name() + "'.", e);
		}
	}

	public void setWorkItemStartButtonLabels(List<String> buttonLabels) {
		if (buttonLabels.equals(getWorkItemStartButtonLabels())) {
			return;
		}
		final IEclipsePreferences node = getButtonLabelsNode();
		clearNode(node);
		for (int i = 0; i < buttonLabels.size(); i++) {
			node.put(String.valueOf(i), buttonLabels.get(i));
		}
		flushNode(node);
	}

	private void clearNode(final IEclipsePreferences node) {
		try {
			node.clear();
		} catch (BackingStoreException e) {
			throw new RuntimeException("Error during clearing node: " + node.name(), e);
		}
	}

	private void flushNode(final IEclipsePreferences node) {
		try {
			node.flush();
		} catch (BackingStoreException e) {
			throw new RuntimeException("Error during flushing preference '" + node.name() + "'.", e);
		}
	}
}
