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
package org.tobbaumann.wt.ui.views.date;

import java.util.Date;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.set.ISetChangeListener;
import org.eclipse.core.databinding.observable.set.SetChangeEvent;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MToolItem;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.databinding.viewers.ObservableSetContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.ui.event.Events;
import org.tobbaumann.wt.ui.views.SwitchComposite;
import org.tobbaumann.wt.ui.views.Switchable;
import org.tobbaumann.wt.ui.views.ViewerUtils;

import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;

public class DatesView implements Switchable {

	private WorkTrackingService service;
	private ESelectionService selectionService;

	private @Inject MPart part;
	private SwitchComposite switchComposite;
	private Composite datesPanel;
	private Composite settingsPanel;
	private TableViewer viewer;

	private final ViewerSelectionOnNewDateUpdater selectionUpdater;


	@Inject
	public DatesView(WorkTrackingService service, ESelectionService selectionService) {
		this.service = service;
		this.selectionService = selectionService;
		this.selectionUpdater = new ViewerSelectionOnNewDateUpdater();
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new FillLayout());
		switchComposite = new SwitchComposite(parent);
		createDatesPanel();
		createSettingsPanel();
		switchComposite.setTopControl(determineTopControlFromToolItemState());
	}

	private void createDatesPanel() {
		datesPanel = new Composite(switchComposite, SWT.NONE);
		datesPanel.setLayout(new FillLayout());
		viewer = new TableViewer(datesPanel, SWT.FULL_SELECTION);
		viewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		viewer.setUseHashlookup(true);
		viewer.setContentProvider(new ObservableSetContentProvider());
		viewer.setLabelProvider(new DatesViewLabelProvider());
		viewer.setComparator(new ViewerComparator(Ordering.natural().reverse()));
		viewer.setInput(service.readDates());
		updateSelectionServiceIfViewerSelectionChanges();
		updateViewerSelectionIfNewDateAdded(service.readDates());
		ViewerUtils.requestFocusOnMouseEnter(viewer);
	}

	private void updateSelectionServiceIfViewerSelectionChanges() {
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				Date date = (Date) ((IStructuredSelection) event.getSelection()).getFirstElement();
				selectionService.setSelection(date);
			}
		});
	}

	private void updateViewerSelectionIfNewDateAdded(IObservableSet dates) {
		// this listener registration must be after setInput,
		// otherwise the ContentProvider has not updated the viewer and
		// the selection can't be set.
		dates.addSetChangeListener(selectionUpdater);
	}

	private void createSettingsPanel() {
		settingsPanel = new DatesViewSettings(switchComposite);
	}

	private Composite determineTopControlFromToolItemState() {
		MToolItem item = getSettingsToolItem();
		return item.isSelected()
				? settingsPanel
				: datesPanel;
	}

	@Override
	public void switchToolItemState() {
		getSettingsToolItem().setSelected(!getSettingsToolItem().isSelected());
	}

	// TODO how get the MToolItem in a better and more robust way?
	private MToolItem getSettingsToolItem() {
		return (MToolItem) part.getToolbar().getChildren().get(0);
	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void requestFocus() {
		if (viewer != null && !viewer.getTable().isDisposed()) {
			viewer.getTable().setFocus();
		}
	}

	@Override
	public void switchPanel() {
		switchComposite.switchActiveControl();
		viewer.refresh(true);
	}

	@Inject @Optional
	void startViewerSelectionUpdating(@UIEventTopic(Events.END_IMPORT) String s) {
		this.selectionUpdater.startUpdating();
	}

	@Inject @Optional
	void stopViewerSelectionUpdating(@UIEventTopic(Events.START_IMPORT) String s) {
		this.selectionUpdater.stopUdating();
	}

	/**
	 *
	 * @author tobbaumann
	 *
	 */
	private final class ViewerSelectionOnNewDateUpdater implements ISetChangeListener {

		private boolean performUpdate = true;

		@Override
		public void handleSetChange(SetChangeEvent event) {
			if (!performUpdate) {
				return;
			}
			Set<?> added = event.diff.getAdditions();
			if (!added.isEmpty()) {
				update(added);
			}
		}

		private void update(Set<?> added) {
			final Date d = (Date) Iterables.getLast(added);
			viewer.setSelection(new StructuredSelection(d));
			requestFocus();
		}

		void startUpdating() {
			performUpdate = true;
		}

		void stopUdating() {
			performUpdate = false;
		}
	}
}
