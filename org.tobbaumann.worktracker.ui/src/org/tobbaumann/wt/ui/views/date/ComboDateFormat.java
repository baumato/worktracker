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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.tobbaumann.wt.ui.preferences.WorkTrackerPreferences;

class ComboDateFormat {
	static ComboDateFormat SHORT = new ComboDateFormat("short", DateFormat.SHORT);
	static ComboDateFormat MEDIUM = new ComboDateFormat("medium", DateFormat.MEDIUM);
	static ComboDateFormat LONG = new ComboDateFormat("long", DateFormat.LONG);
	static ComboDateFormat CUSTOM = new ComboDateFormat("custom", -1);

	final String displayNamePrefix;
	final int dateFormatStyle;

	private ComboDateFormat(String displayNamePrefix, int dateFormatStyle) {
		this.displayNamePrefix = displayNamePrefix;
		this.dateFormatStyle = dateFormatStyle;
	}

	static List<ComboDateFormat> comboDateFormats() {
		return Arrays.asList(SHORT, MEDIUM, LONG, CUSTOM);
	}

	static ComboDateFormat instance(int dateFormatStyle) {
		switch (dateFormatStyle) {
			case DateFormat.SHORT:
				return SHORT;
			case DateFormat.MEDIUM:
				return MEDIUM;
			case DateFormat.LONG:
				return LONG;
			default:
				return CUSTOM;
		}
	}

	String getDisplayName(WorkTrackerPreferences prefs) {
		return displayNamePrefix + " (" + getDateFormat(prefs).format(new Date()) + ")";
	}

	private DateFormat getDateFormat(WorkTrackerPreferences prefs) {
		return dateFormatStyle == -1
				? new SimpleDateFormat(prefs.getDatesViewDateFormatPattern())
				: DateFormat.getDateInstance(dateFormatStyle);
	}

	public boolean isCustomDateFormat() {
		return this == CUSTOM;
	}
}
