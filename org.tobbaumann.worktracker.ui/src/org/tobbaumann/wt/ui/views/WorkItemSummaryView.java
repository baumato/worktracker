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

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.databinding.observable.list.IListChangeListener;
import org.eclipse.core.databinding.observable.list.ListChangeEvent;
import org.eclipse.core.databinding.observable.list.ListDiffVisitor;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.domain.WorkItemSummary;

public class WorkItemSummaryView {

	private TableViewer tableViewer;

	@Inject
	private WorkTrackingService service;

	@Inject
	private ESelectionService selectionService;

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
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
		service.getWorkItems().addListChangeListener(new WorkItemSummariesUpdater());

		Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(false);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		List<String> columnNames = Arrays.asList("Activity", "Duration");
		for (String colName : columnNames) {
			TableColumn tcol = new TableColumn(table, SWT.LEFT);
			tcol.setText(colName);
		}
		new TableColumn(table, SWT.LEFT); // empty column
		packColumns();
	}

	private void packColumns() {
		for (TableColumn c : tableViewer.getTable().getColumns()) {
			c.pack();
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

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void focus() {
		if (tableViewer != null && !tableViewer.getTable().isDisposed()) {
			tableViewer.getTable().setFocus();
		}
	}

	private static final class LabelProvider extends StyledCellLabelProvider {
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
	}

	private final class WorkItemSummariesUpdater implements IListChangeListener {
		@Override
		public void handleListChange(ListChangeEvent event) {
			event.diff.accept(new ListDiffVisitor() {
				@Override
				public void handleRemove(int index, Object element) {
					update(element);
				}

				@Override
				public void handleAdd(int index, Object element) {
					update(element);
				}

				private void update(Object element) {
					List<WorkItemSummary> wis = (List<WorkItemSummary>) tableViewer.getInput();
					Date date = wis.get(0).getWorkItems().get(0).getStart();
					updateDate(date);
				}
			});
		}
	}
}
