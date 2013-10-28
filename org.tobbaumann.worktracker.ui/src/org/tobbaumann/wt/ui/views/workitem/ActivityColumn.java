package org.tobbaumann.wt.ui.views.workitem;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.domain.Activity;
import org.tobbaumann.wt.domain.WorkItem;
import org.tobbaumann.wt.ui.views.wistart.ActivityContentProposalAdapter;

import com.google.common.base.Optional;

/**
 *
 * @author tobbaumann
 *
 */
class ActivityColumn extends WorkItemsViewColumn {

	private static final String COLUMN_ACTIVITY = "Activity";
	private final WorkTrackingService service;

	public ActivityColumn(TableViewer viewer, WorkTrackingService service) {
		super(viewer);
		this.service = service;
	}

	@Override
	String getName() {
		return COLUMN_ACTIVITY;
	}

	@Override
	protected Object getValue(Object element) {
		return ((WorkItem) element).getActivityName();
	}

	@Override
	protected String getStringValue(WorkItem wi) {
		return wi.getActivityName();
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return new ActivityColumnCellEditor(((TableViewer) getViewer()).getTable(), service);
	}

	@Override
	protected void setValue(Object element, Object value) {
		String activityName = value.toString();
		Optional<Activity> oa = service.getActivity(activityName);
		if (oa.isPresent()) {
			WorkItem wi = (WorkItem) element;
			wi.setActivity(oa.get());
			getViewer().update(element, null);
		} else {
			throw new RuntimeException(activityName + " not yet available.");
		}
	}

	/**
	 *
	 * @author tobbaumann
	 *
	 */
	private static class ActivityColumnCellEditor extends TextCellEditor {

		private final WorkTrackingService service;

		public ActivityColumnCellEditor(Composite parent, WorkTrackingService s) {
			super(parent);
			this.service = s;
			setValidator(new ActivityColumnCellEditorValidator(service));
			ActivityContentProposalAdapter.applyContentAssist(text, service);
		}

		private static final class ActivityColumnCellEditorValidator implements ICellEditorValidator {

			private final WorkTrackingService service;

			public ActivityColumnCellEditorValidator(WorkTrackingService service) {
				this.service = service;
			}

			@Override
			public String isValid(Object value) {
				return service.getActivity(value.toString()).isPresent()
						? null
						: "Please choose one of the existing activities.";
			}
		}
	}
}