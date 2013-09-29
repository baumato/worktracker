/*******************************************************************************
 * Copyright (c) 2013 tobba_000. All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: tobba_000 - initial API and implementation
 ******************************************************************************/
package org.tobbaumann.wt.ui.preferences;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newTreeSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.osgi.service.prefs.BackingStoreException;

/**
 *
 * Keep default values in sync with plugin_customization.ini
 * @author tobbaumann
 *
 */
@Creatable
public class WorkTrackerPreferences {

	public static enum TimePeriod {
		DAILY,
		WEEKLY
	}

	public static final String NODE_NAME = "org.tobbaumann.worktracker.ui";

	public static final String USE_REMINDER = "useReminder";
	private static final Boolean USE_REMINDER_DEFAULT = true;
	public static final String REMIND_FREQUENCY = "remindFrequency";
	private static final long REMIND_FREQUENCY_DEFAULT = TimeUnit.MILLISECONDS.convert(15, TimeUnit.MINUTES);

	public static final String STATUS_LINE_UPDATE_FREQUENCY = "updateFrequency";
	private static final long STATUS_LINE_UPDATE_FREQUENCY_DEFAULT = 1000;

	public static final String DATES_VIEW_DATE_FORMAT_STYLE = "dateFormatStyle";
	private static final String DATES_VIEW_DATE_FORMAT_STYLE_DISPLAY_NAME = "Date Format";
	private static final int DATES_VIEW_DATE_FORMAT_STYLE_DEFAULT = DateFormat.SHORT;
	public static final String DATES_VIEW_DATE_FORMAT_PATTERN = "dateFormatPattern";
	private static final String DATES_VIEW_DATE_FORMAT_PATTERN_DISPLAY_NAME = "Date Format Pattner";
	private static final String DATES_VIEW_DATE_FORMAT_PATTERN_DEFAULT = "yyyy-MM-dd";
	public static final String DATES_VIEW_SHOW_WEEKDAYS = "showWeekdays";
	private static final String DATES_VIEW_SHOW_WEEKDAYS_DISPLAY_NAME = "Show Weekdays";
	private static final boolean DATES_VIEW_SHOW_WEEKDAYS_DEFAULT = true;

	public static final String WORKITEM_SUMMARY_TIME_PERIOD = "summaryTimePeriod";
	private static final String WORKITEM_SUMMARY_TIME_PERIOD_DEFAULT = "DAILY";
	public static final String WORKITEM_SUMMARY_MAX_HRS_PER_DAY = "summaryMaxHrsPerDay";
	private static final int WORKITEM_SUMMARY_MAX_HRS_PER_DAY_DEFAULT = 8;
	public static final String WORKITEM_SUMMARY_MAX_HRS_PER_WEEK = "summaryMaxHrsPerWeek";
	private static final int WORKITEM_SUMMARY_MAX_HRS_PER_WEEK_DEFAULT = 40;

	public static final String STARTWI_VIEW_NUMBER_OF_BUTTONS = "numberOfButtons";
	private static final String STARTWI_VIEW_NUMBER_OF_BUTTONS_DISPLAY_NAME = "Number Of Buttons";
	private static final int STARTWI_VIEW_NUMBER_OF_BUTTONS_DEFAULT_VALUE = 6;
	private static final int STARTWI_VIEW_NUMBER_OF_BUTTONS_MAXIMUM_VALUE = 20;
	public static final String STARTWI_VIEW_NUMBER_OF_BUTTON_COLUMNS = "numberOfButtonColumns";
	private static final String STARTWI_VIEW_NUMBER_OF_BUTTON_COLUMNS_DISPLAY_NAME = "Number Of Button Columns";
	private static final int STARTWI_VIEW_NUMBER_OF_BUTTON_COLUMNS_DEFAULT_VALUE = 1;
	private static final int STARTWI_VIEW_NUMBER_OF_BUTTON_COLUMNS_MAXIMUM_VALUE = 6;
	private static final String STARTWI_VIEW_BUTTON_LABELS = "buttonLabels";
	public static final String STARTWI_VIEW_BUTTON_LABELS_NODE_PATH = NODE_NAME + "/" + STARTWI_VIEW_BUTTON_LABELS;


	private final IPreferencesService prefService;

	@Inject
	public WorkTrackerPreferences(IPreferencesService prefService) {
		this.prefService = checkNotNull(prefService);
	}

	/*
	 * Reminder
	 */

	public long getRemindFrequencyInMillis() {
		return prefService.getLong(NODE_NAME, REMIND_FREQUENCY, REMIND_FREQUENCY_DEFAULT, null);
	}

	public void setRemindFrequencyInMillis(long millis) {
		InstanceScope.INSTANCE.getNode(NODE_NAME).putLong(REMIND_FREQUENCY, millis);
		IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode(NODE_NAME);
		node.putLong(REMIND_FREQUENCY, millis);
		flushNode(node);
	}

	public boolean getUseReminder() {
		return prefService.getBoolean(NODE_NAME, USE_REMINDER, USE_REMINDER_DEFAULT, null);
	}

	public void setUseReminder(boolean useReminder) {
		InstanceScope.INSTANCE.getNode(NODE_NAME).putBoolean(USE_REMINDER, useReminder);
		IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode(NODE_NAME);
		node.putBoolean(USE_REMINDER, useReminder);
		flushNode(node);
	}


	/*
	 * StatusLine
	 */

	public long getStatusLineUpdateFrequencyInMillis() {
		return prefService.getLong(NODE_NAME, STATUS_LINE_UPDATE_FREQUENCY, STATUS_LINE_UPDATE_FREQUENCY_DEFAULT, null);
	}

	public void setStatusLineUpdateFrequencyInMillis(long millis) {
		InstanceScope.INSTANCE.getNode(NODE_NAME).putLong(STATUS_LINE_UPDATE_FREQUENCY, millis);
		IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode(NODE_NAME);
		node.putLong(STATUS_LINE_UPDATE_FREQUENCY, millis);
		flushNode(node);
	}


	/*
	 * DatesView
	 */

	public String getDatesViewDateFormatDisplayName() {
		return DATES_VIEW_DATE_FORMAT_STYLE_DISPLAY_NAME;
	}

	public DateFormat getDatesViewDateFormat() {
		Integer dfStyle = getDatesViewDateFormatStyle();
		return dfStyle == -1
				? new SimpleDateFormat(getDatesViewDateFormatPattern())
				: DateFormat.getDateInstance(dfStyle);
	}

	public int getDatesViewDateFormatStyle() {
		return prefService.getInt(NODE_NAME, DATES_VIEW_DATE_FORMAT_STYLE, DATES_VIEW_DATE_FORMAT_STYLE_DEFAULT, null);
	}

	public void setDatesViewDateFormatStyle(int dfStyle) {
		InstanceScope.INSTANCE.getNode(NODE_NAME).putInt(DATES_VIEW_DATE_FORMAT_STYLE, dfStyle);
		IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode(NODE_NAME);
		node.putInt(DATES_VIEW_DATE_FORMAT_STYLE, dfStyle);
		flushNode(node);
	}

	public String getDatesViewDateFormatPatternDisplayName() {
		return DATES_VIEW_DATE_FORMAT_PATTERN_DISPLAY_NAME;
	}

	public String getDatesViewDateFormatPattern() {
		return prefService.getString(NODE_NAME, DATES_VIEW_DATE_FORMAT_PATTERN, DATES_VIEW_DATE_FORMAT_PATTERN_DEFAULT, null);
	}

	public void setDatesViewDateFormatPattern(String pattern) {
		InstanceScope.INSTANCE.getNode(NODE_NAME).put(DATES_VIEW_DATE_FORMAT_PATTERN, pattern);
		IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode(NODE_NAME);
		node.put(DATES_VIEW_DATE_FORMAT_PATTERN, pattern);
		flushNode(node);
	}

	public String getDatesViewShowWeekdaysDisplayName() {
		return DATES_VIEW_SHOW_WEEKDAYS_DISPLAY_NAME;
	}

	public boolean getDatesViewShowWeekdays() {
		return prefService.getBoolean(NODE_NAME, DATES_VIEW_SHOW_WEEKDAYS, DATES_VIEW_SHOW_WEEKDAYS_DEFAULT, null);
	}

	public void setDatesViewShowWeekdays(boolean showWeekdays) {
		InstanceScope.INSTANCE.getNode(NODE_NAME).putBoolean(DATES_VIEW_SHOW_WEEKDAYS, showWeekdays);
		IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode(NODE_NAME);
		node.putBoolean(DATES_VIEW_SHOW_WEEKDAYS, showWeekdays);
		flushNode(node);
	}


	/*
	 * WorkItemSummarySettings
	 */
	public TimePeriod getWorkItemSummaryTimePeriod() {
		String timePeriod = prefService.getString(NODE_NAME, WORKITEM_SUMMARY_TIME_PERIOD, WORKITEM_SUMMARY_TIME_PERIOD_DEFAULT, null);
		return TimePeriod.valueOf(timePeriod);
	}

	public void setWorkItemSummaryTimePeriod(TimePeriod timePeriod) {
		InstanceScope.INSTANCE.getNode(NODE_NAME).put(WORKITEM_SUMMARY_TIME_PERIOD, timePeriod.name());
		IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode(NODE_NAME);
		node.put(WORKITEM_SUMMARY_TIME_PERIOD, timePeriod.name());
		flushNode(node);
	}

	public int getMaximumNumberHoursPerDay() {
		return prefService.getInt(NODE_NAME, WORKITEM_SUMMARY_MAX_HRS_PER_DAY, WORKITEM_SUMMARY_MAX_HRS_PER_DAY_DEFAULT, null);
	}

	public void setMaximumNumberHoursPerDay(int maxHrsPerDay) {
		InstanceScope.INSTANCE.getNode(NODE_NAME).putInt(WORKITEM_SUMMARY_MAX_HRS_PER_DAY, maxHrsPerDay);
		IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode(NODE_NAME);
		node.putInt(WORKITEM_SUMMARY_MAX_HRS_PER_DAY, maxHrsPerDay);
		flushNode(node);
	}

	public int getMaximumNumberOfHoursPerWeek() {
		return prefService.getInt(NODE_NAME, WORKITEM_SUMMARY_MAX_HRS_PER_WEEK, WORKITEM_SUMMARY_MAX_HRS_PER_WEEK_DEFAULT, null);
	}

	public void setMaximumNumberHoursPerWeek(int maxHrsPerWeek) {
		InstanceScope.INSTANCE.getNode(NODE_NAME).putInt(WORKITEM_SUMMARY_MAX_HRS_PER_WEEK, maxHrsPerWeek);
		IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode(NODE_NAME);
		node.putInt(WORKITEM_SUMMARY_MAX_HRS_PER_WEEK, maxHrsPerWeek);
		flushNode(node);
	}


	/*
	 * StartWorkItemWithButtonView
	 */

	public String getNumberOfButtonsDisplayName() {
		return STARTWI_VIEW_NUMBER_OF_BUTTONS_DISPLAY_NAME;
	}

	public int getNumberOfButtons() {
		return prefService.getInt(NODE_NAME, STARTWI_VIEW_NUMBER_OF_BUTTONS, STARTWI_VIEW_NUMBER_OF_BUTTONS_DEFAULT_VALUE, null);
	}

	public void setNumberOfButtons(int numberOfButtons) {
		InstanceScope.INSTANCE.getNode(NODE_NAME).putInt(STARTWI_VIEW_NUMBER_OF_BUTTONS, numberOfButtons);
		IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode(NODE_NAME);
		node.putInt(STARTWI_VIEW_NUMBER_OF_BUTTONS, numberOfButtons);
		flushNode(node);
	}

	public int getMaximumNumberOfButtons() {
		return STARTWI_VIEW_NUMBER_OF_BUTTONS_MAXIMUM_VALUE;
	}

	public int getNumberOfButtonColumns() {
		return prefService.getInt(NODE_NAME, STARTWI_VIEW_NUMBER_OF_BUTTON_COLUMNS, STARTWI_VIEW_NUMBER_OF_BUTTON_COLUMNS_DEFAULT_VALUE, null);
	}

	public String getNumberOfButtonColumnsDisplayName() {
		return STARTWI_VIEW_NUMBER_OF_BUTTON_COLUMNS_DISPLAY_NAME;
	}

	public void setNumberOfButtonColumns(int numberOfButtonColumns) {
		InstanceScope.INSTANCE.getNode(NODE_NAME).putInt(STARTWI_VIEW_NUMBER_OF_BUTTON_COLUMNS, numberOfButtonColumns);
		IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode(NODE_NAME);
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
		return ConfigurationScope.INSTANCE.getNode(STARTWI_VIEW_BUTTON_LABELS_NODE_PATH);
	}

	public List<String> getWorkItemStartButtonLabels() {
		if (!customWorkItemStartButtonLabelsAvailable()) {
			return Collections.emptyList();
		}
		IEclipsePreferences node = getButtonLabelsNode();
		try {
			List<String> activityNames = newArrayList();
			TreeSet<Integer> keySet = newTreeSet();
			for (String k : node.keys()) {
				keySet.add(Integer.valueOf(k));
			}
			for (Integer key : keySet) {
				activityNames.add(node.get(String.valueOf(key), ""));
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
