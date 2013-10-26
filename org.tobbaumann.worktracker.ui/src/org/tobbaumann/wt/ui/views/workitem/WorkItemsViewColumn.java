package org.tobbaumann.wt.ui.views.workitem;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.TextStyle;
import org.tobbaumann.wt.domain.WorkItem;

/**
 *
 * @author tobbaumann
 *
 */
abstract class WorkItemsViewColumn extends EditingSupport {

	private final CellLabelProvider labelProvider;

	public WorkItemsViewColumn(TableViewer viewer) {
		super(viewer);
		this.labelProvider = new WorkItemsViewColumnLabelProvider(viewer);
	}

	abstract String getName();

	public CellLabelProvider getLabelProvider() {
		 return labelProvider;
	}

	@Override
	protected abstract Object getValue(Object element);

	protected String getStringValue(WorkItem wi) {
		return getValue(wi).toString();
	}

	@Override
	protected abstract boolean canEdit(Object element);

	@Override
	protected CellEditor getCellEditor(Object element) {
		return null;
	}

	@Override
	protected void setValue(Object element, Object value) {

	}


	/**
	 *
	 * @author tobbaumann
	 *
	 */
	private final class WorkItemsViewColumnLabelProvider extends StyledCellLabelProvider {

		private final Styler activeWorkItemStyler;

		public WorkItemsViewColumnLabelProvider(TableViewer viewer) {
			this.activeWorkItemStyler = new ActiveWorkItemStyler(viewer);
		}

		@Override
		public void update(ViewerCell cell) {
			WorkItem wi = (WorkItem) cell.getElement();
			StyledString text = new StyledString();
			text.append(getStringValue(wi), createStyler(wi));
			cell.setText(text.toString());
			cell.setStyleRanges(text.getStyleRanges());
		}

		private Styler createStyler(WorkItem wi) {
			return isActiveWorkItem(wi) ? activeWorkItemStyler : null;
		}

		private boolean isActiveWorkItem(WorkItem wi) {
			return wi.getEndDate() == null;
		}
	}


	/**
	 *
	 * @author tobbaumann
	 *
	 */
	private static class ActiveWorkItemStyler extends Styler {

		private final String fontName;

		ActiveWorkItemStyler(Viewer viewer) {
			this.fontName = viewer.getControl().getFont().getFontData()[0].getName();
		}

		@Override
		public void applyStyles(TextStyle textStyle) {
			textStyle.font = JFaceResources.getFontRegistry().getBold(fontName);
		}
	}


	/**
	 *
	 * @author tobbaumann
	 *
	 */
	static class ActivityColumn extends WorkItemsViewColumn {

		private static final String COLUMN_ACTIVITY = "Activity";

		public ActivityColumn(TableViewer viewer) {
			super(viewer);
		}

		@Override
		String getName() {
			return COLUMN_ACTIVITY;
		}

		@Override
		protected Object getValue(Object element) {
			return ((WorkItem)element).getActivity().getName();
		}

		@Override
		protected boolean canEdit(Object element) {
			return false;
		}
	}

	/**
	 *
	 * @author tobbaumann
	 *
	 */
	static abstract class DateColumn extends WorkItemsViewColumn {

		public DateColumn(TableViewer viewer) {
			super(viewer);
		}

		@Override
		protected boolean canEdit(Object element) {
			return true;
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			return new TimeCellEditor(((TableViewer)getViewer()).getTable());
		}

		@Override
		protected abstract void setValue(Object element, Object value);

		@Override
		protected String getStringValue(WorkItem wi) {
			return format(wi.getStart());
		}

		private String format(Date date) {
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
			return dateToCalendar(wi.getEnd());
		}

		@Override
		protected void setValue(Object element, Object value) {
			WorkItem wi = (WorkItem) element;
			wi.setEndDate(calendarToDate((Calendar) value));
			getViewer().update(element, null);
		}
	}

	/**
	 *
	 * @author tobbaumann
	 *
	 */
	static class DurationColumn extends WorkItemsViewColumn {

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
	}
}