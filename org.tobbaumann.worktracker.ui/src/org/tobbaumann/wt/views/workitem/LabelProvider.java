package org.tobbaumann.wt.views.workitem;

import static com.google.common.base.Objects.firstNonNull;

import java.text.DateFormat;
import java.util.Date;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;
import org.tobbaumann.wt.domain.WorkItem;


final class LabelProvider extends StyledCellLabelProvider implements ILabelProvider {

	@Override
	public void update(ViewerCell cell) {
		WorkItem wi = (WorkItem) cell.getElement();
		switch (cell.getColumnIndex()) {
		case 0:
			cell.setText(wi.getActivityName());
			break;
		case 1:
			cell.setText(format(wi.getStart()));
			break;
		case 2:
			final Date d = firstNonNull(wi.getEnd(), new Date());
			cell.setText(format(d));
			break;
		case 3:
			cell.setText(wi.getDuration().toString());
			break;
		default:
			break;
		}
		super.update(cell);
	}

	private String format(Date date) {
		// TODO should be preference
		return DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
	}

	@Override
	public Image getImage(Object element) {
		return null;
	}

	@Override
	// ILabelProvider#getText used during sorting the viewer
	public String getText(Object element) {
		WorkItem item = (WorkItem) element;
		return String.valueOf(item.getStart().getTime());
	}
}