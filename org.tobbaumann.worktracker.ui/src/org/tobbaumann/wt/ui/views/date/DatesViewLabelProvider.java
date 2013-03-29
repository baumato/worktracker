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

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;

/**
 *
 * @author tobbaumann
 *
 */
public class DatesViewLabelProvider extends StyledCellLabelProvider implements ILabelProvider {

	private final SimpleDateFormat weekDayFormat;

	DatesViewLabelProvider() {
		this.weekDayFormat = new SimpleDateFormat("EE");
	}

	@Override
	public void update(ViewerCell cell) {
		Date date = (Date) cell.getElement();
		StringBuilder cellText = new StringBuilder();
		cellText.append(format(date));
		if (shouldShowWeekdays()) {
			cellText.append(" (").append(weekDayFormat.format(date)).append(")");
		}
		cell.setText(cellText.toString());
		super.update(cell);
	}

	private String format(Date date) {
		return DatesViewPreference.getCurrentDateFormat().format(date);
	}

	private Boolean shouldShowWeekdays() {
		return Boolean.valueOf(DatesViewPreference.SHOW_WEEKDAYS.getValue());
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
