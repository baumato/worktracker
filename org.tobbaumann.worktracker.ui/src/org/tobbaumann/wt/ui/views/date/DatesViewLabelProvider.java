package org.tobbaumann.wt.ui.views.date;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

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

	DatesViewLabelProvider(UserProfile userProfile) {
		this.userProfile = userProfile;
		this.weekDayFormat = new SimpleDateFormat("EE", DateFormatSymbols.getInstance(userProfile
				.getLocale()));
	}

	@Override
	public void update(ViewerCell cell) {
		Date date = (Date) cell.getElement();
		cell.setText(userProfile.getDateFormat().format(date) + " (" + weekDayFormat.format(date)
				+ ")");
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