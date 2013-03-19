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
package org.tobbaumann.wt.core.impl;

import static com.google.common.base.Objects.firstNonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tobbaumann.wt.core.UserProfile;

public class DefaultUserProfile implements UserProfile {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultUserProfile.class);

	private Locale locale;
	private DateFormat dateFormat;
	private DateFormat timeFormat;

	public void activate() {
		LOGGER.trace("activate");
	}

	public void deactivate() {
		LOGGER.trace("deactivate");
	}

	@Inject
	@Optional
	public void updateLocale(@Preference(nodePath = "user_profile", value = "locale") String localeLanguageTag) {
		this.locale = Locale.forLanguageTag(localeLanguageTag);
		Locale.setDefault(locale);
	}

	@Override
	public Locale getLocale() {
		return firstNonNull(locale, Locale.getDefault());
	}

	@Inject
	@Optional
	public void updateDateFormat(@Preference(nodePath = "user_profile", value = "date_format") String dateFormat) {
		this.dateFormat = new SimpleDateFormat(dateFormat);
	}

	@Override
	public DateFormat getDateFormat() {
		return firstNonNull(dateFormat, DateFormat.getDateInstance(DateFormat.SHORT, getLocale()));
	}

	@Inject
	@Optional
	public void updateTimeFormat(@Preference(nodePath = "user_profile", value = "time_format") String timeFormat) {
		this.timeFormat = new SimpleDateFormat(timeFormat);
	}

	@Override
	public DateFormat getTimeFormat() {
		return firstNonNull(timeFormat, DateFormat.getTimeInstance(DateFormat.SHORT, getLocale()));
	}
}
