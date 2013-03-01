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

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.domain.Activities;
import org.tobbaumann.wt.domain.Activity;

import com.google.common.collect.Ordering;

public class ChangeActivityView {

	private WorkTrackingService service;

	private TableViewer activitiesTable;

	@Inject
	public ChangeActivityView(WorkTrackingService service) {
		this.service = service;
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		activitiesTable = new TableViewer(parent, SWT.FULL_SELECTION);
		activitiesTable.getTable().setHeaderVisible(false);
		activitiesTable.getTable().setLinesVisible(false);
		activitiesTable.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		activitiesTable.setContentProvider(new ArrayContentProvider());
		activitiesTable.setLabelProvider(new ChangeActivitiesViewLabelProvider());
		activitiesTable.setComparator(new ViewerComparator(Ordering.natural()));
		updateActivitiesTable();
	}

	private void updateActivitiesTable() {
		Activities activities = service.readActivities();
		activitiesTable.setInput(activities.getActivities());
	}

	@PreDestroy
	public void dispose() {
	}

	private class ChangeActivitiesViewLabelProvider extends StyledCellLabelProvider {

		Styler styler = new FrequenzStyler();

		@Override
		public void update(ViewerCell cell) {
			Activity a = (Activity) cell.getElement();
			StyledString text = new StyledString();
			text.append(a.getName(), StyledString.DECORATIONS_STYLER);
			text.append(" (" + a.getOccurrenceFrequency() + ") ", styler);
			cell.setText(text.toString());
			cell.setStyleRanges(text.getStyleRanges());
			super.update(cell);
		}

		private final class FrequenzStyler extends Styler {
			private static final String FREQUENZ_STYLER_FONT = "FrequenzStylerFont";
			FrequenzStyler() {
				JFaceResources.getColorRegistry().put(JFacePreferences.COUNTER_COLOR, new RGB(0,127,174));
				FontData[] fd = activitiesTable.getTable().getFont().getFontData();
				fd[0].setHeight(fd[0].getHeight()-2);
				JFaceResources.getFontRegistry().put(FREQUENZ_STYLER_FONT, fd);
			}

			@Override
			public void applyStyles(TextStyle textStyle) {
				textStyle.font = JFaceResources.getFontRegistry().get(FREQUENZ_STYLER_FONT);
				Color color = JFaceResources.getColorRegistry().get(JFacePreferences.COUNTER_COLOR);
				textStyle.foreground = color;
			}
		}
	}
}
