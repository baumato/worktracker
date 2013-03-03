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

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.set.ISetChangeListener;
import org.eclipse.core.databinding.observable.set.SetChangeEvent;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.databinding.viewers.ObservableSetContentProvider;
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
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.ui.UserProfile;

import com.google.common.collect.Ordering;

public class DatesView {

	@Inject
	private WorkTrackingService wtService;

	@Inject
	private UserProfile userProfile;

	@Inject
	private ESelectionService selectionService;

	private TableViewer viewer;

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		viewer = new TableViewer(parent, SWT.FULL_SELECTION);
		viewer.setContentProvider(new ObservableSetContentProvider());
		viewer.setLabelProvider(new DatesViewLabelProvider());
		viewer.setComparator(new ViewerComparator(Ordering.natural().reverse()));
		IObservableSet dates = wtService.readDates();
		dates.addSetChangeListener(new ISetChangeListener() {
			@Override
			public void handleSetChange(SetChangeEvent event) {
				//FIXME
				//				Set<?> added = event.diff.getAdditions();
				//				Date d = (Date) Iterables.getLast(added);
				//				selectionService.setSelection(d);
			}
		});
		viewer.setInput(dates);
		viewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				Date date = (Date) ((IStructuredSelection) event.getSelection()).getFirstElement();
				selectionService.setSelection(date);
			}
		});
	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void focus() {
		if (viewer != null && !viewer.getTable().isDisposed()) {
			viewer.getTable().setFocus();
		}
	}



	private class DatesViewLabelProvider extends StyledCellLabelProvider implements ILabelProvider {
		@Override
		public void update(ViewerCell cell) {
			Date date = (Date) cell.getElement();
			cell.setText(userProfile.getDateFormat().format(date));
			super.update(cell);
		}

		@Override
		public Image getImage(Object element) {
			return null;
		}

		@Override
		// ILabelProvider#getText used during sorting the viewer
		public String getText(Object element) {
			Date date = (Date) element;
			return String.valueOf(date.getTime());
		}
	}
}
