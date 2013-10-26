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


import static com.google.common.collect.Lists.newArrayList;

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
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.domain.WorkItem;
import org.tobbaumann.wt.ui.views.OnWorkItemListChangeUpdater;
import org.tobbaumann.wt.ui.views.ViewerUtils;
import org.tobbaumann.wt.ui.views.workitem.WorkItemsViewColumn.ActivityColumn;
import org.tobbaumann.wt.ui.views.workitem.WorkItemsViewColumn.DurationColumn;
import org.tobbaumann.wt.ui.views.workitem.WorkItemsViewColumn.EndDateColumn;
import org.tobbaumann.wt.ui.views.workitem.WorkItemsViewColumn.StartDateColumn;

import com.google.common.collect.Ordering;

/**
 *
 * @author tobbaumann
 *
 */
public class WorkItemsView {

	private TableViewer tableViewer;
	private WorkTrackingService service;

	private List<WorkItemsViewColumn> columns = newArrayList();

	@Inject
	private ESelectionService selectionService;

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
		applyEditingSupport();
	}

	private void createAndConfigureTableViewer(Composite parent) {
		tableViewer = new TableViewer(parent, SWT.FULL_SELECTION);
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setLabelProvider(new LabelProviderForSortingPurposes());
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				Object selectedObject = ((IStructuredSelection)event.getSelection()).getFirstElement();
				selectionService.setSelection(selectedObject);
			}
		});
		tableViewer.setComparator(new ViewerComparator(Ordering.natural().reverse()));
		service.getWorkItems().addListChangeListener(new WorkItemsUpdater());
		ViewerUtils.refreshViewerPeriodically(tableViewer);
	}

	private void createAndConfigureTable() {
		Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(false);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	private void createColumns() {
		columns = Arrays.asList(
			new ActivityColumn(tableViewer),
			new StartDateColumn(tableViewer),
			new EndDateColumn(tableViewer),
			new DurationColumn(tableViewer));
		Table table = tableViewer.getTable();
		for (WorkItemsViewColumn col : columns) {
			TableColumn tcol = new TableColumn(table, SWT.LEFT);
			tcol.setText(col.getName());
		}
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

	private void applyEditingSupport() {
		TableColumn[] tableColumns = tableViewer.getTable().getColumns();
		for (int i = 0; i < tableColumns.length; i++) {
			TableColumn tc = tableColumns[i];
			TableViewerColumn column = new TableViewerColumn(tableViewer, tc);
			column.setEditingSupport(columns.get(i));
			column.setLabelProvider(columns.get(i).getLabelProvider());
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


	/**
	 *
	 * @author tobbaumann
	 *
	 */
	private final class WorkItemsUpdater extends OnWorkItemListChangeUpdater {

		@Override
		protected Date getCurrentlySelectedDate() {
			List<WorkItem> items = (List<WorkItem>) tableViewer.getInput();
			return items == null || items.isEmpty() ? new Date() : items.get(0).getStart();
		}

		@Override
		protected void update(Date date) {
			updateDate(date);
		}
	}


}
