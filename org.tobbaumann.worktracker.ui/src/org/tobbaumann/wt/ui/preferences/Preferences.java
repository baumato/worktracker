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
package org.tobbaumann.wt.ui.preferences;

import java.util.concurrent.TimeUnit;

public class Preferences {
	public static final String REMINDER = "worktracker.preferences.reminder";
	public static final String USE_REMINDER = "useReminder";
	public static final Boolean USE_REMINDER_DEFAULT = true;
	public static final String REMIND_FREQUENCY = "remindFrequency";
	public static final long REMIND_FREQUENCY_DEFAULT = TimeUnit.MILLISECONDS.convert(15, TimeUnit.MINUTES);
}
