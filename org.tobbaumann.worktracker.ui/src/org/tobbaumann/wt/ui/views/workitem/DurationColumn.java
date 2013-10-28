package org.tobbaumann.wt.ui.views.workitem;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.tobbaumann.wt.domain.WorkItem;

/**
 *
 * @author tobbaumann
 *
 */
class DurationColumn extends WorkItemsViewColumn {

	private static final String COLUMN_DURATION = "Duration";

	public DurationColumn(TableViewer viewer) {
		super(viewer);
	}

	@Override
	String getName() {
		return COLUMN_DURATION;
	}

	@Override
	protected Object getValue(Object element) {
		WorkItem wi = (WorkItem) element;
		return wi.getDuration().toString();
	}

	@Override
	protected boolean canEdit(Object element) {
		return false;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return null;
	}

	@Override
	protected void setValue(Object element, Object value) {
		// nothing to do
	}
}