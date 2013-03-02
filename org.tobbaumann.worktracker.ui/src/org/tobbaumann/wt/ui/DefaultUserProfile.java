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
package org.tobbaumann.wt.ui;

import java.text.DateFormat;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultUserProfile implements UserProfile {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultUserProfile.class);

	public void activate() {
		LOGGER.trace("activate");
	}

	public void deactivate() {
		LOGGER.trace("deactivate");
	}

	@Override
	public Locale getLocale() {
		return Locale.getDefault();
	}

	@Override
	public DateFormat getDateFormat() {
		return DateFormat.getDateInstance(DateFormat.SHORT, getLocale());
	}

	@Override
	public DateFormat getTimeFormat() {
		return DateFormat.getTimeInstance(DateFormat.SHORT, getLocale());
	}
}
