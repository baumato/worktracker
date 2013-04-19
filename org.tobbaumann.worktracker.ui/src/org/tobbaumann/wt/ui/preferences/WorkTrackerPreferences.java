/*******************************************************************************
 * Copyright (c) 2013 tobba_000. All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: tobba_000 - initial API and implementation
 ******************************************************************************/
package org.tobbaumann.wt.ui.preferences;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.services.log.Logger;
import org.osgi.service.prefs.BackingStoreException;

@Creatable
public class WorkTrackerPreferences {

	public static final String REMINDER = "worktracker.preferences.reminder";
	public static final String USE_REMINDER = "useReminder";
	public static final Boolean USE_REMINDER_DEFAULT = true;
	public static final String REMIND_FREQUENCY = "remindFrequency";
	public static final long REMIND_FREQUENCY_DEFAULT = TimeUnit.MILLISECONDS.convert(
		15,
		TimeUnit.MINUTES);

	public static final String STATUS_LINE = "worktracker.preferences.statusLine";
	public static final String STATUS_LINE_UPDATE_FREQUENCY = "updateFrequency";
	public static final long STATUS_LINE_UPDATE_FREQUENCY_DEFAULT = 1000;

	private @Inject
	Logger logger;

	public long getRemindFrequencyInMillis() {
		return ConfigurationScope.INSTANCE.getNode(REMINDER).getLong(
			REMIND_FREQUENCY,
			REMIND_FREQUENCY_DEFAULT);
	}

	public void setRemindFrequencyInMillis(long millis) {
		IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode(REMINDER);
		node.putLong(REMIND_FREQUENCY, millis);
		flushNode(node);
	}

	private void flushNode(IEclipsePreferences node) {
		try {
			node.flush();
		} catch (BackingStoreException e) {
			logger.error(e, "Error during flushing preference 'remindFrequencyInMillis'");
		}
	}

	public boolean getUseReminder() {
		return ConfigurationScope.INSTANCE.getNode(REMINDER).getBoolean(
			USE_REMINDER,
			USE_REMINDER_DEFAULT);
	}

	public void setUseReminder(boolean useReminder) {
		IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode(REMINDER);
		node.putBoolean(USE_REMINDER, useReminder);
		flushNode(node);
	}

	public long getStatusLineUpdateFrequencyInMillis() {
		return ConfigurationScope.INSTANCE.getNode(STATUS_LINE).getLong(
			STATUS_LINE_UPDATE_FREQUENCY,
			STATUS_LINE_UPDATE_FREQUENCY_DEFAULT);
	}

	public void setStatusLineUpdateFrequencyInMillis(long millis) {
		IEclipsePreferences node = ConfigurationScope.INSTANCE.getNode(STATUS_LINE);
		node.putLong(STATUS_LINE_UPDATE_FREQUENCY, millis);
		flushNode(node);
	}
}
