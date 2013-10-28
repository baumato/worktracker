package org.tobbaumann.wt.ui.views.workitem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.tobbaumann.wt.domain.WorkItem;

/**
 *
 * @author tobbaumann
 *
 */
abstract class DateColumn extends WorkItemsViewColumn {

	public DateColumn(TableViewer viewer) {
		super(viewer);
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected String getStringValue(WorkItem wi) {
		final Calendar cal = (Calendar) getValue(wi);
		return format(cal.getTime());
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return new TimeCellEditor(((TableViewer)getViewer()).getTable());
	}

	String format(Date date) {
		// TODO should be preference?
		return DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
	}

	protected Calendar dateToCalendar(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		return cal;
	}

	protected Date calendarToDate(Calendar c) {
		return c.getTime();
	}

	boolean equal(Object oldValue, Object newValue) {
		Calendar calOldValue = (Calendar) oldValue;
		Calendar calNewValue = (Calendar) newValue;
		SimpleDateFormat f = new SimpleDateFormat("HHmmss");
		String strOldValue = f.format(calOldValue.getTime());
		String strNewValue = f.format(calNewValue.getTime());
		return strOldValue.equals(strNewValue);
	}


	/**
	 *
	 * @author tobbaumann
	 *
	 */
	static class StartDateColumn extends DateColumn {

		private static final String COLUMN_START = "Start";

		public StartDateColumn(TableViewer viewer) {
			super(viewer);
		}

		@Override
		String getName() {
			return COLUMN_START;
		}

		@Override
		protected Object getValue(Object element) {
			WorkItem wi = (WorkItem) element;
			return dateToCalendar(wi.getStart());
		}

		@Override
		protected void setValue(Object element, Object value) {
			WorkItem wi = (WorkItem) element;
			wi.setStart(calendarToDate((Calendar) value));
			getViewer().update(element, null);
		}
	}

	/**
	 *
	 * @author tobbaumann
	 *
	 */
	static class EndDateColumn extends DateColumn {

		private static final String COLUMN_END = "End";

		private Object oldValue;

		public EndDateColumn(TableViewer viewer) {
			super(viewer);
		}

		@Override
		String getName() {
			return COLUMN_END;
		}

		@Override
		protected Object getValue(Object element) {
			WorkItem wi = (WorkItem) element;
			oldValue = dateToCalendar(wi.getEnd());
			return oldValue;
		}

		@Override
		protected void setValue(Object element, Object value) {
			if (!equal(oldValue, value)) {
				WorkItem wi = (WorkItem) element;
				wi.setEndDate(calendarToDate((Calendar) value));
				getViewer().update(element, null);
			}
		}
	}
}