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
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MToolItem;
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
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.domain.DomainFactory;
import org.tobbaumann.wt.domain.WorkItemSummaries;
import org.tobbaumann.wt.domain.WorkItemSummary;
import org.tobbaumann.wt.ui.preferences.WorkTrackerPreferences;
import org.tobbaumann.wt.ui.preferences.WorkTrackerPreferences.TimePeriod;
import org.tobbaumann.wt.ui.views.OnWorkItemListChangeUpdater;
import org.tobbaumann.wt.ui.views.PreferencesComposite;
import org.tobbaumann.wt.ui.views.SwitchComposite;
import org.tobbaumann.wt.ui.views.Switchable;
import org.tobbaumann.wt.ui.views.ViewerUtils;

public class WorkItemSummaryView implements Switchable {

	private WorkTrackingService service;
	private ESelectionService selectionService;
	private WorkTrackerPreferences prefs;

	private MPart part;

	private SwitchComposite switchComposite;
	private Composite mainPage;
	private PreferencesComposite settingsPage;
	private TableViewer tableViewer;
	private CLabel statusLine;

	private Date activeDate;

	@Inject
	public WorkItemSummaryView(MPart part,
			WorkTrackingService service,
			ESelectionService selectionService,
			WorkTrackerPreferences prefs) {
		this.part = part;
		this.service = service;
		this.selectionService = selectionService;
		this.prefs = prefs;
	}

	@Override
	public void switchToolItemState() {
		getSettingsToolItem().setSelected(!getSettingsToolItem().isSelected());
	}

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
		if (switchComposite.getTopControl() == settingsPage) {
			settingsPage.flushPreferences();
		}
	}

	private void activate() {
		if (switchComposite.getTopControl() == settingsPage) {
			updateSettingsPage();
		}
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		switchComposite = new SwitchComposite(parent);
		createMainPage(switchComposite);
		createSettingsPage(switchComposite);
		switchComposite.setTopControl(determineTopControlFromToolItemState());
	}

	private void createMainPage(Composite parent) {
		mainPage = createMainPageComposite(parent);
		createTable(mainPage);
		createStatusLine(mainPage);
	}

	private Composite createMainPageComposite(Composite parent) {
		Composite parentComp = new Composite(parent, SWT.NONE);
		parentComp.setLayout(new GridLayout(1, false));
		return parentComp;
	}

	private void createTable(Composite parentComp) {
		Composite tableComp = new Composite(parentComp, SWT.NONE);
		tableComp.setLayout(new FillLayout());
		tableComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		createAndConfigureTableViewer(tableComp);
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
	}

	private void createColumns() {
		List<String> columnNames = Arrays.asList("Activity", "Duration", "Ratio");
		for (String colName : columnNames) {
			TableColumn tcol = new TableColumn(tableViewer.getTable(), SWT.LEFT);
			tcol.setText(colName);
		}
	}

	private void packColumns() {
		for (TableColumn c : tableViewer.getTable().getColumns()) {
			c.pack();
		}
	}

	private void createStatusLine(Composite parentComp) {
		Composite statusLineComp = new Composite(parentComp, SWT.NONE);
		statusLineComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		statusLineComp.setLayout(new FillLayout());
		statusLine = new CLabel(statusLineComp, SWT.NONE);
	}

	private void createSettingsPage(Composite parent) {
		settingsPage = new WorkItemSummaryViewSettings(parent, prefs);
	}

	private Composite determineTopControlFromToolItemState() {
		MToolItem item = getSettingsToolItem();
		return item.isSelected()
				? settingsPage
				: mainPage;
	}

	@Inject
	public void updateDate(@Named(IServiceConstants.ACTIVE_SELECTION) @Optional Date date) {
		activeDate = date;
		if (activeDate == null) {
			return;
		}
		if (tableViewer.getControl().isDisposed()) {
			return;
		}
		final List<WorkItemSummary> wis;
		switch (prefs.getWorkItemSummaryTimePeriod()) {
			case DAILY:
				wis = service.getWorkItemSummaries(activeDate);
				updateStatusLine(wis, date);
				break;

			case WEEKLY:
				Calendar c = Calendar.getInstance();
				c.setTime(activeDate);
				int weekInYear = c.get(Calendar.WEEK_OF_YEAR);
				wis = service.getWorkItemSummaries(weekInYear);
				updateStatusLine(wis, weekInYear);
				break;

			default:
				wis = Collections.emptyList();
				break;
		}
		tableViewer.setInput(wis);
		packColumns();
	}

	private void updateStatusLine(List<WorkItemSummary> wis, Date activeDate) {
		WorkItemSummaries w = createWorkItemSummaries(wis);
		String msg = String.format(
				"Summary of %s:\n  Sum of duration %s\n  Sum of ratio %s",
				formatDate(activeDate),
				w.getSumOfDurations().toString(),
				w.computeSumOfDurationRatio(getTotalMinutes()));
		statusLine.setText(msg);
		statusLine.getParent().getParent().layout();
	}

	private WorkItemSummaries createWorkItemSummaries(List<WorkItemSummary> wis) {
		WorkItemSummaries w = DomainFactory.eINSTANCE.createWorkItemSummaries();
		w.getWorkItemSummaries().addAll(wis);
		return w;
	}

	private String formatDate(Date activeDate) {
		return prefs.getDatesViewDateFormat().format(activeDate);
	}

	private void updateStatusLine(List<WorkItemSummary> wis, int weekInYear) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.WEEK_OF_YEAR, weekInYear);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		String startDate = formatDate(c.getTime());
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		String endDate = formatDate(c.getTime());
		WorkItemSummaries ws = createWorkItemSummaries(wis);
		statusLine.setText(String.format(
				"Summary of week %s: %s - %s:\n  Sum of duration %s\n  Sum of ratio %s",
				weekInYear,
				startDate,
				endDate,
				ws.getSumOfDurations(),
				ws.computeSumOfDurationRatio(getTotalMinutes())));
		statusLine.getParent().getParent().layout();
	}

	private int getTotalMinutes() {
		return (prefs.getWorkItemSummaryTimePeriod() == TimePeriod.DAILY
				? prefs.getMaximumNumberHoursPerDay()
				: prefs.getMaximumNumberOfHoursPerWeek()) * 60;
	}

	void switchSummary() {
		TimePeriod curSummary = prefs.getWorkItemSummaryTimePeriod();
		TimePeriod newSummary = curSummary == TimePeriod.DAILY ? TimePeriod.WEEKLY : TimePeriod.DAILY;
		prefs.setWorkItemSummaryTimePeriod(newSummary);
	}

	@Inject
	@org.eclipse.e4.core.di.annotations.Optional
	public void updateSummaryTimePeriod(@Preference(value = WorkTrackerPreferences.WORKITEM_SUMMARY_TIME_PERIOD) String timePeriod) {
		((MToolItem) part.getToolbar().getChildren().get(1)).setSelected(timePeriod.equals(TimePeriod.WEEKLY.name()));
		updateSettingsPage();
		updateDate(activeDate);
	}

	@Inject
	@org.eclipse.e4.core.di.annotations.Optional
	public void updateDailyHours(@Preference(value = WorkTrackerPreferences.WORKITEM_SUMMARY_MAX_HRS_PER_DAY) int hours) {
		updateSettingsPage();
		updateDate(activeDate);
	}

	private void updateSettingsPage() {
		if (settingsPage != null && !settingsPage.isDisposed()) {
			settingsPage.updatePreferenceFields();
		}
	}

	@Inject
	@org.eclipse.e4.core.di.annotations.Optional
	public void updateWeeklyHours(@Preference(value = WorkTrackerPreferences.WORKITEM_SUMMARY_MAX_HRS_PER_WEEK) int hours) {
		updateSettingsPage();
		updateDate(activeDate);
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
	 * @author tobbaumann
	 */
	final class WorkItemSummaryViewLabelProvider extends StyledCellLabelProvider
			implements ILabelProvider {

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
				case 2:
					List<WorkItemSummary> wis = (List<WorkItemSummary>) tableViewer.getInput();
					WorkItemSummaries w = createWorkItemSummaries(wis);
					cell.setText(w.computeDurationRatio(s, getTotalMinutes()).toString());
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
			WorkItemSummary s = (WorkItemSummary) element;
			return s.getActivityName();
		}
	}


	/**
	 *
	 * @author tobbaumann
	 *
	 */
	private final class WorkItemSummariesUpdater extends OnWorkItemListChangeUpdater {

		@Override
		protected Date getCurrentlySelectedDate() {
			List<?> input = (List<?>) tableViewer.getInput();
			if (input == null || input.isEmpty()) {
				return new Date();
			}
			WorkItemSummary wis = (WorkItemSummary) input.get(0);
			return getDateFromElement(wis.getWorkItems().get(0));
		}

		@Override
		protected void update(Date date) {
			updateDate(date);
		}
	}
}
