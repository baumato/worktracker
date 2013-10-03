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

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
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
import org.tobbaumann.wt.domain.WorkItem;
import org.tobbaumann.wt.ui.event.Events;
import org.tobbaumann.wt.ui.preferences.WorkTrackerPreferences;
import org.tobbaumann.wt.ui.views.PreferencesComposite;
import org.tobbaumann.wt.ui.views.SwitchComposite;
import org.tobbaumann.wt.ui.views.Switchable;
import org.tobbaumann.wt.ui.views.ViewerUtils;

import com.google.common.collect.Ordering;

public class DatesView implements Switchable {

	private @Inject WorkTrackingService service;
	private @Inject ESelectionService selectionService;
	private @Inject WorkTrackerPreferences prefs;
	private @Inject MPart part;

	private SwitchComposite switchComposite;
	private Composite datesPanel;
	private PreferencesComposite settingsPanel;
	private TableViewer viewer;

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
		viewer.setLabelProvider(new DatesViewLabelProvider(prefs));
		viewer.setComparator(new ViewerComparator(Ordering.natural().reverse()));
		viewer.setInput(service.readDates());
		updateSelectionServiceIfViewerSelectionChanges();
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

	private void createSettingsPanel() {
		settingsPanel = new DatesViewSettings(switchComposite, prefs);
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

	@Override
	public void switchPanel() {
		deactivate();
		switchComposite.switchActiveControl();
		activate();
	}

	private void deactivate() {
		if (switchComposite.getTopControl() == settingsPanel) {
			settingsPanel.flushPreferences();
		}
	}

	private void activate() {
		if (switchComposite.getTopControl() == settingsPanel) {
			settingsPanel.updatePreferenceFields();
		}
	}

	@PreDestroy
	public void dispose() {
	}

	@Inject
	@Optional
	void workItemStarted(@UIEventTopic(Events.START_WORK_ITEM)  com.google.common.base.Optional<WorkItem> optStartedWorkItem) {
		if (!optStartedWorkItem.isPresent()) {
			return;
		}
		WorkItem wi = optStartedWorkItem.get();
		viewer.setSelection(new StructuredSelection(wi.getDatePartOfStart()));
		requestFocus();
	}

	// TODO Is following stmt correct: This method gets called if anything in the DATES_VIEW_NODE_NAME has been changed, not only if
	// the DATE_FORMAT has been changed.
	@Inject
	@org.eclipse.e4.core.di.annotations.Optional
	public void updateViewerOnDateFormatChange(@Preference(value = WorkTrackerPreferences.DATES_VIEW_DATE_FORMAT_STYLE) int dateFormatStyle) {
		if (viewerNotNullOrDisposed()) {
			viewer.refresh(true);
		}
	}

	private boolean viewerNotNullOrDisposed() {
		return viewer != null && !viewer.getControl().isDisposed();
	}

	@Focus
	public void requestFocus() {
		if (viewerNotNullOrDisposed()) {
			viewer.getTable().setFocus();
		}
	}
}
