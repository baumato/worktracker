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
package org.tobbaumann.wt.ui.views;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.databinding.observable.list.IListChangeListener;
import org.eclipse.core.databinding.observable.list.ListChangeEvent;
import org.eclipse.core.databinding.observable.list.ListDiffVisitor;
import org.tobbaumann.wt.domain.WorkItem;

public abstract class OnWorkItemListChangeUpdater extends ListDiffVisitor<WorkItem> implements
		IListChangeListener<WorkItem> {

	@Override
	public void handleListChange(ListChangeEvent<WorkItem> event) {
		event.diff.accept(this);
	}

	@Override
	public void handleAdd(int index, WorkItem element) {
		updateIfNecessary(element);
	}

	@Override
	public void handleRemove(int index, WorkItem element) {
		updateIfNecessary(element);
	}

	private void updateIfNecessary(WorkItem element) {
		if (elementRelevantForCurrentlySelectedDate(element)) {
			update(getCurrentlySelectedDate());
		}
	}

	private boolean elementRelevantForCurrentlySelectedDate(WorkItem element) {
		return getDateFormat().format(element.getStart()).equals(
				getDateFormat().format(getCurrentlySelectedDate()));
	}

	private SimpleDateFormat getDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd");
	}

	protected abstract Date getCurrentlySelectedDate();

	protected abstract void update(Date date);
}
