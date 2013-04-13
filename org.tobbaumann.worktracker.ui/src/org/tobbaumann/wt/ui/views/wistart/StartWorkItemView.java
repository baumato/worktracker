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
package org.tobbaumann.wt.ui.views.wistart;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.base.Strings.nullToEmpty;
import static com.google.common.collect.Lists.newArrayList;

import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.Comparator;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.databinding.observable.list.IListChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.ListChangeEvent;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MToolItem;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.domain.Activity;
import org.tobbaumann.wt.ui.event.Events;
import org.tobbaumann.wt.ui.views.ViewerUtils;

import com.google.common.collect.Ordering;

public class StartWorkItemView {

	private @Inject WorkTrackingService service;
	private @Inject IEventBroker eventBroker;

	private @Inject MPart part;
	private Text txtActivity;
	private ContentProposalAdapter activiyContentProposalAdapter;
	private Spinner startedSpinner;
	private Button btnAdd;
	private TableViewer activitiesTable;
	private ActivitiesTableFilter activitiesTableFilter;

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void requestFocus() {
		if (txtActivity != null && !txtActivity.isDisposed()) {
			txtActivity.setFocus();
		}
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	private void createControls(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		createActivitiesTableStripe(parent);
		createAndConfigureActivitiesTable(parent);
		updateActivitiesTable();
	}

	private void createActivitiesTableStripe(Composite parent) {
		Composite stripe = new Composite(parent, SWT.NONE);
		stripe.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		stripe.setLayout(new GridLayout(3, false));
		createActivityTextField(stripe);
		createStartedNumberOfMintuesBeforeSpinner(stripe);
		createStartWorkItemButton(stripe);
	}

	private void createActivityTextField(Composite stripe) {
		txtActivity = new Text(stripe, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		txtActivity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		txtActivity.setMessage("Enter activity here...");
		txtActivity.setToolTipText("Enter the name of your activity you want to start.");
		txtActivity.addKeyListener(new StartWorkItemOnKeyShortcutListener());
		applyConentAssist(txtActivity);
		updateAddButtonEnabling();
	}

	private void applyConentAssist(Text txtActivity) {
		SimpleContentProposalProvider proposalProvider = new SimpleContentProposalProvider(
				createProposalsFromActivities(service.getActivities()));
		activiyContentProposalAdapter = new ContentProposalAdapter(
			txtActivity,
			new TextContentAdapter(),
			proposalProvider,
			null,
			null);
		proposalProvider.setFiltering(true);
		activiyContentProposalAdapter.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
		updateProposalsOnActivityListChange(proposalProvider);
	}

	private String[] createProposalsFromActivities(IObservableList activities) {
		Collection<String> activityNames = newArrayList();
		for (Object o : activities) {
			Activity a = (Activity) o;
			activityNames.add(a.getName());
		}
		String[] arrActivityNames = activityNames.toArray(new String[0]);
		return arrActivityNames;
	}

	private void updateProposalsOnActivityListChange(final SimpleContentProposalProvider proposalProvider) {
		service.getActivities().addListChangeListener(new IListChangeListener() {
			@Override
			public void handleListChange(ListChangeEvent event) {
				IObservableList activities = event.getObservableList();
				String[] arrActivityNames = createProposalsFromActivities(activities);
				proposalProvider.setProposals(arrActivityNames);
			}
		});
	}

	private void updateAddButtonEnabling() {
		txtActivity.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				btnAdd.setEnabled(!nullToEmpty(txtActivity.getText()).trim().isEmpty());
			}
		});
	}

	private void createStartedNumberOfMintuesBeforeSpinner(Composite stripe) {
		startedSpinner = new Spinner (stripe, SWT.BORDER);
		startedSpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		startedSpinner.setMinimum(0);
		startedSpinner.setMaximum(24*60);
		startedSpinner.setSelection(0);
		startedSpinner.setIncrement(1);
		startedSpinner.setPageIncrement(10);
		startedSpinner.setToolTipText("Enter how many minutes ago this activity has been started?");
		startedSpinner.addKeyListener(new StartWorkItemOnKeyShortcutListener());
	}

	private void createStartWorkItemButton(Composite stripe) {
		btnAdd = new Button(stripe, SWT.PUSH);
		btnAdd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		btnAdd.setImage(getAddImage());
		btnAdd.setToolTipText("Starts a new work item with the entered activity.");
		btnAdd.setEnabled(false);
		btnAdd.addKeyListener(new StartWorkItemOnKeyShortcutListener());
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				startWorkItem();
			}
		});
	}

	private Image getAddImage() {
		try {
			ImageRegistry imageRegistry = JFaceResources.getImageRegistry();
			Image imgAdd = imageRegistry.get("Add_Icon");
			if (imgAdd == null) {
				URL imgUrl = URI.create("platform:/plugin/org.tobbaumann.worktracker.ui/icons/pc.de/plus.png").toURL();
				ImageDescriptor imageDescriptor = ImageDescriptor.createFromURL(imgUrl);
				imageRegistry.put("Add_Icon", imageDescriptor);
				return imageRegistry.get("Add_Icon");
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	private void createAndConfigureActivitiesTable(Composite parent) {
		activitiesTable = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE);
		activitiesTable.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		activitiesTable.setLabelProvider(new ChangeActivitiesViewLabelProvider());
		activitiesTable.setContentProvider(new ObservableListContentProvider());
		activitiesTable.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (event.getSelection().isEmpty()) {
					return;
				}
				Activity selectedActivity = (Activity) ((IStructuredSelection)event.getSelection()).getFirstElement();
				txtActivity.setText(selectedActivity.getName());
			}
		});
		activitiesTable.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				if (event.getSelection().isEmpty()) {
					return;
				}
				IStructuredSelection s = (IStructuredSelection) event.getSelection();
				Activity a = (Activity) s.getFirstElement();
				startWorkItem(a.getName());
			}
		});
		applySorting();
		applyFilter();
		applyMenu();
		ViewerUtils.requestFocusOnMouseEnter(activitiesTable);
		makeActivitiesTableDragSource();
	}

	private void applySorting() {
		// TODO how get the MToolItem in a more robust way?
		MToolItem item = (MToolItem) part.getToolbar().getChildren().get(0);
		if (item.isSelected()) {
			sortActivitiesByUsage();
		} else {
			sortActivitiesByName();
		}
	}

	void sortActivitiesByUsage() {
		activitiesTable.setComparator(new ViewerComparator(new UsageComparator()));
	}

	void sortActivitiesByName() {
		activitiesTable.setComparator(new ViewerComparator(Ordering.natural()));
	}

	private void applyFilter() {
		this.activitiesTableFilter = new ActivitiesTableFilter(!getShowUnusedActivitiesToolItem().isSelected());
		activitiesTable.addFilter(activitiesTableFilter);
	}

	// TODO how get the MToolItem in a better and more robust way?
	private MToolItem getShowUnusedActivitiesToolItem() {
		return (MToolItem) part.getToolbar().getChildren().get(2);
	}

	private void applyMenu() {
		activitiesTable.getTable().addMenuDetectListener(new MenuDetectListener() {
			@Override
			public void menuDetected(MenuDetectEvent e) {
				createAndShowMenu();
			}
		});
	}

	private void createAndShowMenu() {
		Menu menu = new Menu(activitiesTable.getTable().getShell(), SWT.POP_UP);
		IStructuredSelection sel = (IStructuredSelection) activitiesTable.getSelection();
		Activity a = (Activity) sel.getFirstElement();
		if (a.isInUse()) {
			addHideActions(menu, a);
		} else {
			addShowActions(menu, a);
		}
		if (menu.getItemCount() > 0) {
			menu.setVisible(true);
		}
	}


	private void addShowActions(Menu menu, final Activity a) {
		if (!activitiesTable.getSelection().isEmpty()) {
			MenuItem miShow = new MenuItem(menu, SWT.NONE);
			miShow.setText("Mark in use");
			miShow.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					a.setInUse(true);
					activitiesTable.refresh();
				}
			});
		}

		if (service.getActivities().size() > 1) {
			MenuItem miShowAll = new MenuItem(menu, SWT.NONE);
			miShowAll.setText("Mark all in use");
			miShowAll.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					for (Object o : service.getActivities()) {
						Activity a = (Activity) o;
						a.setInUse(true);
					}
					activitiesTable.refresh();
				}
			});
		}
	}

	private void addHideActions(Menu menu, final Activity a) {
		if (!activitiesTable.getSelection().isEmpty()) {
			MenuItem miHide = new MenuItem(menu, SWT.NONE);
			miHide.setText("Mark unused");
			miHide.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					a.setInUse(false);
					activitiesTable.refresh();
				}
			});
		}

		if (service.getActivities().size() > 1) {
			MenuItem miHideAll = new MenuItem(menu, SWT.NONE);
			miHideAll.setText("Mark all unused");
			miHideAll.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					for (Object o : service.getActivities()) {
						Activity a = (Activity) o;
						a.setInUse(false);
					}
					activitiesTable.refresh();
				}
			});
		}
	}

	private void makeActivitiesTableDragSource() {
		DragSource ds = new DragSource(activitiesTable.getTable(), DND.DROP_MOVE);
		ds.setTransfer(new Transfer[] { TextTransfer.getInstance() });
		ds.addDragListener(new DragSourceAdapter() {
			@Override
			public void dragSetData(DragSourceEvent event) {
				System.out.println("dragSetData");
				IStructuredSelection selection = (IStructuredSelection) activitiesTable.getSelection();
				event.data = ((Activity)selection.getFirstElement()).getName();
			}
		});
	}

	void toggleUnusedActivities() {
		this.activitiesTableFilter.toggleFilterUsage();
		this.activitiesTable.refresh();
	}

	private void startWorkItem() {
		startWorkItem(txtActivity.getText());
	}

	private void startWorkItem(String activityName) {
		if (isNullOrEmpty(activityName)) {
			return;
		}
		service.startWorkItem(activityName, startedSpinner.getSelection());
		eventBroker.send(Events.START_WORK_ITEM, service.getActiveWorkItem());
	}

	@Inject @Optional
	void workItemStarted(@SuppressWarnings("unused") @UIEventTopic(Events.START_WORK_ITEM) Object o) {
		txtActivity.setText("");
		startedSpinner.setSelection(0);
	}

	private void updateActivitiesTable() {
		IObservableList activities = service.getActivities();
		activitiesTable.setInput(activities);
	}

	/**
	 *
	 * @author tobbaumann
	 *
	 */
	private final class StartWorkItemOnKeyShortcutListener extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			if ((enterPressed(e) || altAPressed(e)) && !isProposalPopupOpen()) {
				startWorkItem();
			}
		}

		private boolean enterPressed(KeyEvent e) {
			return e.character == SWT.CR;
		}

		private boolean altAPressed(KeyEvent e) {
			return (e.stateMask & SWT.ALT) != 0
					&& e.character == 'a';
		}

		private boolean isProposalPopupOpen() {
			return activiyContentProposalAdapter != null && activiyContentProposalAdapter.isProposalPopupOpen();
		}
	}

	/**
	 *
	 * @author tobbaumann
	 *
	 */
	private final class UsageComparator implements Comparator<String> {
		@Override
		public int compare(String o1, String o2) {
			String s1 = getUsageOutOfString(o1);
			String s2 = getUsageOutOfString(o2);
			return Long.valueOf(s2).compareTo(Long.valueOf(s1));
		}

		private String getUsageOutOfString(String o1) {
			return o1.substring(o1.lastIndexOf('(')+1, o1.length()-1);
		}
	}

	/**
	 *
	 * @author tobbaumann
	 *
	 */
	private class ChangeActivitiesViewLabelProvider extends StyledCellLabelProvider implements ILabelProvider {

		private Styler styler = new FrequenzStyler();

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

		@Override
		public Image getImage(Object element) {
			return null;
		}

		@Override
		public String getText(Object element) {
			Activity a = (Activity) element;
			return a.getName() + " (" + a.getOccurrenceFrequency() + ")";
		}

		private final class FrequenzStyler extends Styler {

			private static final String FREQUENZ_STYLER_FONT = "FrequenzStylerFont";

			FrequenzStyler() {
				JFaceResources.getColorRegistry().put(JFacePreferences.COUNTER_COLOR,
						new RGB(0, 127, 174));
				FontData[] fd = activitiesTable.getTable().getFont().getFontData();
				fd[0].setHeight(fd[0].getHeight() - 2);
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

	/**
	 *
	 * @author tobbaumann
	 *
	 */
	private static final class ActivitiesTableFilter extends ViewerFilter {

		private boolean filterActive;

		public ActivitiesTableFilter(boolean filterActive) {
			this.filterActive = filterActive;
		}

		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			if (!filterActive) {
				return true;
			}
			Activity a = (Activity) element;
			return a.isInUse();
		}

		void toggleFilterUsage() {
			filterActive = !filterActive;
		}
	}
}
