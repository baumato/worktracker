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
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.domain.WorkItemSummary;
import org.tobbaumann.wt.ui.views.OnWorkItemListChangeUpdater;
import org.tobbaumann.wt.ui.views.ViewerUtils;

public class WorkItemSummaryView {

	private WorkTrackingService service;
	private ESelectionService selectionService;

	private TableViewer tableViewer;

	@Inject
	public WorkItemSummaryView(WorkTrackingService service, ESelectionService selectionService) {
		this.service = service;
		this.selectionService = selectionService;
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
		tableViewer.setLabelProvider(new WorkItemSummaryViewLabelProvider());
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				Object selectedObject = ((IStructuredSelection)event.getSelection()).getFirstElement();
				selectionService.setSelection(selectedObject);
			}
		});
		service.getWorkItems().addListChangeListener(new WorkItemSummariesUpdater());
		tableViewer.setComparator(new ViewerComparator());
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
		List<String> columnNames = Arrays.asList("Activity", "Duration");
		for (String colName : columnNames) {
			TableColumn tcol = new TableColumn(tableViewer.getTable(), SWT.LEFT);
			tcol.setText(colName);
		}
	}

	@Inject
	public void updateDate(@Named(IServiceConstants.ACTIVE_SELECTION) @Optional Date date) {
		if (date == null) {
			return;
		}
		List<WorkItemSummary> wis = service.getWorkItemSummaries(date);
		tableViewer.setInput(wis);
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

	private final class WorkItemSummariesUpdater extends OnWorkItemListChangeUpdater {

		@Override
		protected Date getCurrentlySelectedDate() {
			List<?> wis = (List<?>) tableViewer.getInput();
			return (wis == null || wis.isEmpty()) ? new Date() : getDateFromElement(((WorkItemSummary)wis.get(0)).getWorkItems().get(0));
		}

		@Override
		protected void update(Date date) {
			updateDate(date);
		}
	}
}
