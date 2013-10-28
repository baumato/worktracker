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
	protected abstract CellEditor getCellEditor(Object element);

	@Override
	protected abstract void setValue(Object element, Object value);

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
}
