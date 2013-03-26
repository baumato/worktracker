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

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;
import org.tobbaumann.wt.core.UserProfile;

/**
 *
 * @author tobbaumann
 *
 */
public class DatesViewLabelProvider extends StyledCellLabelProvider implements ILabelProvider {

	private final UserProfile userProfile;
	// TODO weekDayFormat should be in preferences
	private final SimpleDateFormat weekDayFormat;

	private boolean showWeekdays = Boolean.valueOf(org.tobbaumann.wt.ui.views.date.DatesView.Preference.SHOW_WEEKDAYS.defaultValue);

	DatesViewLabelProvider(UserProfile userProfile) {
		this.userProfile = userProfile;
		this.weekDayFormat = new SimpleDateFormat("EE", DateFormatSymbols.getInstance(userProfile
				.getLocale()));
	}

	@Inject
	@Optional
	public void setShowWeekdays(
			@Preference(nodePath = "DatesView", value = "SHOW_WEEKDAYS") boolean showWeekdays) {
		this.showWeekdays = showWeekdays;
	}

	@Override
	public void update(ViewerCell cell) {
		Date date = (Date) cell.getElement();
		StringBuilder cellText = new StringBuilder();
		cellText.append(userProfile.getDateFormat().format(date));
		if (showWeekdays) {
			cellText.append(" (").append(weekDayFormat.format(date)).append(")");
		}
		cell.setText(cellText.toString());
		super.update(cell);
	}

	@Override
	public Image getImage(Object element) {
		return null;
	}

	@Override
	// ILabelProvider#getText used during sorting the viewer
	public String getText(Object element) {
		Date date = (Date) element;
		return String.valueOf(date.getTime());
	}
}
