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

import java.text.DateFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.domain.WorkItem;
import org.tobbaumann.wt.domain.provider.DomainItemProviderAdapterFactory;
import org.tobbaumann.wt.domain.provider.WorkItemItemProvider;

public class ActivitiesView {

	private TableViewer tableViewer;
	private WorkTrackingService service;
	@Inject
	private ESelectionService selectionService;

	@Inject
	public ActivitiesView(WorkTrackingService service) {
		this.service = service;
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {


		tableViewer = new TableViewer(parent, SWT.FULL_SELECTION);
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setLabelProvider(new ActivitiesViewLabelProvider());

		Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final WorkItemItemProvider wip = new WorkItemItemProvider(new DomainItemProviderAdapterFactory());
		for (IItemPropertyDescriptor p : wip.getPropertyDescriptors(null)) {
			TableColumn tcol = new TableColumn(table, SWT.LEFT);
			tcol.setText(p.getDisplayName(null));
		}
		TableColumn emptyCol = new TableColumn(table, SWT.LEFT);
		packColumns();
	}

	private void packColumns() {
		for (TableColumn c : tableViewer.getTable().getColumns()) {
			c.pack();
		}
	}

	@Inject
	public void updateDate(@Named(IServiceConstants.ACTIVE_SELECTION) @Optional String date) {
		if (date == null) {
			return;
		}
		List<WorkItem> workItems = service.readWorkItems(date);
		tableViewer.setInput(workItems);
		packColumns();
	}


	@PreDestroy
	public void dispose() {
	}

	private static final class ActivitiesViewLabelProvider extends StyledCellLabelProvider {
		@Override
		public void update(ViewerCell cell) {
			WorkItem wi = (WorkItem) cell.getElement();




			switch(cell.getColumnIndex()) {
			case 0:
				cell.setText(wi.getID().substring(0, 8));
				break;
			case 1:
				cell.setText(wi.getActivity().getName());
				break;
			case 2:
				String start = DateFormat.getTimeInstance().format(wi.getStart());
				cell.setText(start);
				break;
			case 3:
				cell.setText(DateFormat.getTimeInstance().format(wi.getEnd()));
				break;
			default:
				break;
			}

			// clients must override and configure the cell and call super
			super.update(cell); // calls 'repaint' to trigger the paint listener
		}
	}
}
