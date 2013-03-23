package org.tobbaumann.wt.ui.views;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.databinding.observable.list.IListChangeListener;
import org.eclipse.core.databinding.observable.list.ListChangeEvent;
import org.eclipse.core.databinding.observable.list.ListDiffVisitor;
import org.tobbaumann.wt.domain.WorkItem;

public abstract class OnWorkItemListChangeUpdater extends ListDiffVisitor implements
		IListChangeListener {

	@Override
	public void handleListChange(ListChangeEvent event) {
		event.diff.accept(this);
	}

	@Override
	public void handleAdd(int index, Object element) {
		updateIfNecessary(element);
	}

	@Override
	public void handleRemove(int index, Object element) {
		updateIfNecessary(element);
	}

	private void updateIfNecessary(Object element) {
		if (elementRelevantForCurrentlySelectedDate(element)) {
			update(getCurrentlySelectedDate());
		}
	}

	private boolean elementRelevantForCurrentlySelectedDate(Object element) {
		return getDateFormat().format(getDateFromElement(element)).equals(
				getDateFormat().format(getCurrentlySelectedDate()));
	}

	private SimpleDateFormat getDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd");
	}

	protected Date getDateFromElement(Object element) {
		WorkItem wi = (WorkItem) element;
		return wi.getStart();
	}

	protected abstract Date getCurrentlySelectedDate();

	protected abstract void update(Date date);
}
