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
