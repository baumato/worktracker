/*******************************************************************************
 * Copyright (c) 2013 tobba_000.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     tobba_000 - initial API and implementation
 ******************************************************************************/
package org.tobbaumann.wt.ui.views.workitem;

import java.text.DateFormat;
import java.util.Date;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.TextStyle;
import org.tobbaumann.wt.domain.WorkItem;


final class LabelProvider extends StyledCellLabelProvider implements ILabelProvider {

	private final Viewer viewer;

	LabelProvider(Viewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public void update(ViewerCell cell) {
		WorkItem wi = (WorkItem) cell.getElement();
		StyledString text = new StyledString();
		switch (cell.getColumnIndex()) {
		case 0:
			text.append(wi.getActivityName(), createStyler(wi));
			break;
		case 1:
			text.append(format(wi.getStart()), createStyler(wi));
			break;
		case 2:
			text.append(format(wi.getEnd()), createStyler(wi));
			break;
		case 3:
			text.append(wi.getDuration().toString(), createStyler(wi));
			break;
		default:
			break;
		}
		cell.setText(text.toString());
		cell.setStyleRanges(text.getStyleRanges());
		super.update(cell);
	}

	private Styler createStyler(WorkItem wi) {
		return isActiveWorkItem(wi) ? new ActiveWorkItemStyler() : null;
	}

	private boolean isActiveWorkItem(WorkItem wi) {
		return wi.getEndDate() == null;
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

	/**
	 *
	 * @author tobbaumann
	 *
	 */
	private class ActiveWorkItemStyler extends Styler {

		private final String fontName = viewer.getControl().getFont().getFontData()[0].getName();

		@Override
		public void applyStyles(TextStyle textStyle) {
			textStyle.font = JFaceResources.getFontRegistry().getBold(fontName);
		}
	}
}
