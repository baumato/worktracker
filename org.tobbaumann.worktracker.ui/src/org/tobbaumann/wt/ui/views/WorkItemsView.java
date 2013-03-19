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

import static com.google.common.base.Objects.firstNonNull;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.tobbaumann.wt.core.UserProfile;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.domain.WorkItem;

import com.google.common.collect.Ordering;

public class WorkItemsView {

	private TableViewer tableViewer;
	private WorkTrackingService service;

	@Inject
	private ESelectionService selectionService;

	@Inject
	private UserProfile userProfile;

	@Inject
	public WorkItemsView(WorkTrackingService service) {
		this.service = service;
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		createAndConfigureTableViewer(parent);
		createAndConfigureTable();
		createColumns();
		packColumns();
	}

	private void createAndConfigureTableViewer(Composite parent) {
		tableViewer = new TableViewer(parent, SWT.FULL_SELECTION);
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setLabelProvider(new LabelProvider());
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				Object selectedObject = ((IStructuredSelection)event.getSelection()).getFirstElement();
				selectionService.setSelection(selectedObject);
			}
		});
		tableViewer.setComparator(new ViewerComparator(Ordering.natural().reverse()));
		service.getWorkItems().addListChangeListener(new WorkItemsUpdater());
		ViewerUtils.requestFocusOnMouseEnter(tableViewer);
		ViewerUtils.refreshViewerPeriodically(tableViewer);
	}

	private void createAndConfigureTable() {
		Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(false);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	private void createColumns() {
		Table table = tableViewer.getTable();
		List<String> columnNames = Arrays.asList("Activity", "Start", "End", "Duration");
		for (String colName : columnNames) {
			TableColumn tcol = new TableColumn(table, SWT.LEFT);
			tcol.setText(colName);
		}
		new TableColumn(table, SWT.LEFT); // empty column
	}

	@Inject
	public void updateDate(@Named(IServiceConstants.ACTIVE_SELECTION) @Optional Date date) {
		if (date == null) {
			return;
		}
		tableViewer.setInput(service.getWorkItems(date));
		packColumns();
	}

	private void packColumns() {
		for (TableColumn c : tableViewer.getTable().getColumns()) {
			c.pack();
		}
	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void focus() {
		if (tableViewer != null && !tableViewer.getTable().isDisposed()) {
			tableViewer.getTable().setFocus();
		}
	}


	private final class LabelProvider extends StyledCellLabelProvider implements ILabelProvider {

		@Override
		public void update(ViewerCell cell) {
			WorkItem wi = (WorkItem) cell.getElement();
			switch (cell.getColumnIndex()) {
			case 0:
				cell.setText(wi.getActivityName());
				break;
			case 1:
				cell.setText(userProfile.getTimeFormat().format(wi.getStart()));
				break;
			case 2:
				final Date d = firstNonNull(wi.getEnd(), new Date());
				cell.setText(userProfile.getTimeFormat().format(d));
				break;
			case 3:
				cell.setText(wi.getDuration().toString());
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
			WorkItem item = (WorkItem) element;
			return String.valueOf(item.getStart().getTime());
		}
	}


	private final class WorkItemsUpdater extends OnWorkItemListChangeUpdater {

		@Override
		Date getCurrentlySelectedDate() {
			List<?> items = (List<?>) tableViewer.getInput();
			return items == null || items.isEmpty() ? new Date() : getDateFromElement(items.get(0));
		}

		@Override
		void update(Date date) {
			updateDate(date);
		}
	}
}
