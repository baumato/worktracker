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

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

enum DatesViewPreference {
	DATE_FORMAT("DATE_FORMAT", "Date Format", String.valueOf(DateFormat.SHORT)),
	DATE_FORMAT_PATTERN("DATE_FORMAT_PATTERN", "Date Format Pattern", "yyyy-MM-dd"),
	SHOW_WEEKDAYS("SHOW_WEEKDAYS", "Show weekdays", "true");

	private final String id;
	private final String name;
	private final String defaultValue;

	private DatesViewPreference(String id, String name, String defaultValue) {
		this.id = id;
		this.name = name;
		this.defaultValue = defaultValue;
	}

	String getName() {
		return name;
	}

	String getValue() {
		return getPreferencesStore().get(id, defaultValue);
	}

	void putValue(String value) {
		getPreferencesStore().put(id, value);
		try {
			getPreferencesStore().flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}

	private Preferences getPreferencesStore() {
		return ConfigurationScope.INSTANCE.getNode(DatesView.class.getSimpleName());
	}

	static DateFormat getCurrentDateFormat() {
		Integer dfStyle = Integer.valueOf(DatesViewPreference.DATE_FORMAT.getValue());
		return dfStyle == -1
				? new SimpleDateFormat(getCurrentDateFormatPattern())
				: DateFormat.getDateInstance(dfStyle);
	}

	static String getCurrentDateFormatPattern() {
		return DatesViewPreference.DATE_FORMAT_PATTERN.getValue();
	}
}
