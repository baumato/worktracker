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
package org.tobbaumann.wt.ui.views.wisummary;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;
import org.tobbaumann.wt.domain.WorkItemSummary;

final class WorkItemSummaryViewLabelProvider extends StyledCellLabelProvider implements ILabelProvider {

	@Override
	public void update(ViewerCell cell) {
		WorkItemSummary s = (WorkItemSummary) cell.getElement();
		switch (cell.getColumnIndex()) {
		case 0:
			cell.setText(s.getActivityName());
			break;
		case 1:
			cell.setText(s.getSumOfDurations().toString());
			break;
		default:
			break;
		}
		super.update(cell);
	}

	@Override
	public Image getImage(Object element) {
		return null;
	}

	@Override
	// ILabelProvider#getText used during sorting the viewer
	public String getText(Object element) {
		WorkItemSummary s = (WorkItemSummary) element;
		return s.getActivityName();
	}
}
