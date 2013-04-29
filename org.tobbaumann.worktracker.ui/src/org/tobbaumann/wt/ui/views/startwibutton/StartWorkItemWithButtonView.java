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
package org.tobbaumann.wt.ui.views.startwibutton;

import static com.google.common.collect.Lists.newArrayList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MToolItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.domain.Activity;
import org.tobbaumann.wt.ui.event.Events;
import org.tobbaumann.wt.ui.preferences.WorkTrackerPreferences;
import org.tobbaumann.wt.ui.views.SwitchComposite;
import org.tobbaumann.wt.ui.views.Switchable;

public class StartWorkItemWithButtonView implements Switchable {

	private static final String BTN_DATA_KEY_ACTIVITY_NAME = "activityName";

	private @Inject Logger logger;
	private @Inject WorkTrackingService service;
	private @Inject WorkTrackerPreferences prefs;
	private @Inject IEventBroker eventBroker;
	private @Inject MPart part;

	private SwitchComposite switchComposite;
	private StartWorkItemWithButtonViewSettings settingsPanel;
	private Composite buttonPanel;
	private Composite buttonPanelContent;

	private int nrOfButtons;
	private int nrOfBtnColumns;


	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new FillLayout());
		switchComposite = new SwitchComposite(parent);
		this.buttonPanel = new Composite(switchComposite, SWT.NONE);
		createButtonPanel();
		createSettingsPanel();
		switchComposite.setTopControl(determineTopControlFromToolItemState());
	}

	@Inject
	@Optional
	public void updateNrOfButtons(@Preference(value = WorkTrackerPreferences.STARTWI_VIEW_NUMBER_OF_BUTTONS) int nrOfButtons) {
		if (this.nrOfButtons != nrOfButtons) {
			this.nrOfButtons = nrOfButtons;
			if (buttonPanelContent != null && !buttonPanelContent.isDisposed()) {
				activateButtonPanel();
			}
		}
	}

	@Inject
	@Optional
	public void updateNrOfButtonColumns(@Preference(value = WorkTrackerPreferences.STARTWI_VIEW_NUMBER_OF_BUTTON_COLUMNS) int nrOfBtnColumns) {
		if (this.nrOfBtnColumns != nrOfBtnColumns) {
			this.nrOfBtnColumns = nrOfBtnColumns;
			if (buttonPanelContent != null && !buttonPanelContent.isDisposed()) {
				activateButtonPanel();
			}
		}
	}

	private void activateButtonPanel() {
		this.buttonPanelContent.dispose();
		createButtonPanel();
		switchComposite.layout(true, true);
	}

	private void createButtonPanel() {
		if (prefs.customWorkItemStartButtonLabelsAvailable()) {
			createButtonPanel(prefs.getWorkItemStartButtonLabels());
		} else {
			createMostUsedActivitiesButtons();
		}
	}

	private void createMostUsedActivitiesButtons() {
		List<Activity> activities = service.getMostUsedActivities(nrOfButtons);
		List<String> mua = newArrayList();
		for (Activity a : activities) {
			mua.add(a.getName());
		}
		createButtonPanel(mua);
	}

	private void createButtonPanel(List<String> activityNames) {
		buttonPanel.setLayout(new FillLayout());
		buttonPanelContent = new Composite(buttonPanel, SWT.NONE);
		GridLayout layout = new GridLayout(nrOfBtnColumns, true);
		buttonPanelContent.setLayout(layout);
		for (int i = 0; i < nrOfButtons; i++) {
			int buttonNumber = i+1;
			if (i < activityNames.size()) {
				createButton(buttonPanelContent, activityNames.get(i), buttonNumber);
			} else {
				createButton(buttonPanelContent, "", buttonNumber);
			}
		}
	}

	private void createButton(Composite parent, String activityName, int buttonNumber) {
		final Button btn = new Button(parent, SWT.PUSH | SWT.WRAP | SWT.CENTER);
		btn.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		btn.setData("org.eclipse.e4.ui.css.id", "QuickAccessButton");
		updateButtonWithActivityName(btn, activityName, buttonNumber);
		btn.setToolTipText(activityName);
		startWorkItemOnButtonSelection(btn);
		makeButtonDropTarget(btn);
	}

	private void handleButtonDrop(final Button btn, DropTargetEvent event) {
		if (service.getActivity((String) event.data).isPresent()) {
			updateButtonWithActivityName(btn, event.data.toString(), getButtonIndex(btn)+1);
			saveButtonTextsToPreferences(btn.getParent());
		}
	}

	private void updateButtonWithActivityName(Button btn, String activityName, int buttonNumber) {
		btn.setData(BTN_DATA_KEY_ACTIVITY_NAME, activityName);
		btn.setText(createButtonText(activityName, buttonNumber));
	}

	private String createButtonText(String activityName, int buttonNumber) {
		String buttonText = activityName.isEmpty()
				? ""
				: activityName + " (F"+buttonNumber+")";
		return buttonText;
	}

	private void startWorkItemOnButtonSelection(final Button btn) {
		btn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				startWorkItem(getActivityNameFromButton(btn));
			}
		});
	}

	private void makeButtonDropTarget(final Button btn) {
		DropTarget dt = new DropTarget(btn, DND.DROP_MOVE);
		dt.setTransfer(new Transfer[] { TextTransfer.getInstance() });
		dt.addDropListener(new DropTargetAdapter() {
			@Override
			public void drop(DropTargetEvent event) {
				handleButtonDrop(btn, event);
			}
		});
	}

	private void createSettingsPanel() {
		this.settingsPanel = new StartWorkItemWithButtonViewSettings(switchComposite, prefs);
		this.settingsPanel.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals(StartWorkItemWithButtonViewSettings.FILL_BUTTON_PRESSED)) {
					switchToolItemState();
					switchPanel();
				}
			}
		});
	}

	private Composite determineTopControlFromToolItemState() {
		MToolItem item = getSettingsToolItem();
		return item.isSelected()
				? settingsPanel
				: buttonPanel;
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
			deactivateSettingsPanel();
		}
	}

	private void deactivateSettingsPanel() {
		settingsPanel.flushPreferences();
	}

	private void activate() {
		if (switchComposite.getTopControl() == settingsPanel) {
			activateSettingsPanel();
		}
	}

	private void activateSettingsPanel() {
		settingsPanel.updatePreferenceFields();
	}

	public void startWorkItem(int buttonIndex) {
		if (buttonIndex < buttonPanelContent.getChildren().length) {
			Control c = buttonPanelContent.getChildren()[buttonIndex];
			if (c instanceof Button) {
				startWorkItem(getActivityNameFromButton((Button) c));
			}
		}
	}

	private void startWorkItem(String activityName) {
		if (activityName != null && !activityName.trim().isEmpty()) {
			service.startWorkItem(activityName, 0);
			eventBroker.send(Events.START_WORK_ITEM,
					service.getActiveWorkItem());
		}
	}

	private void saveButtonTextsToPreferences(Composite btnParent) {
		List<String> buttonLabels = newArrayList();
		Control[] buttonList = btnParent.getChildren();
		for (int buttonIndex = 0; buttonIndex < buttonList.length; buttonIndex++) {
			Control c = buttonList[buttonIndex];
			if (c instanceof Button) {
				Button btn = (Button) c;
				buttonLabels.add(getActivityNameFromButton(btn));
			}
		}
		prefs.setWorkItemStartButtonLabels(buttonLabels);
	}

	private String getActivityNameFromButton(Button btn) {
		return btn.getData(BTN_DATA_KEY_ACTIVITY_NAME).toString();
	}
	
	@Focus
	public void requestFocus() {
		if (switchComposite != null && !switchComposite.isDisposed()) {
			switchComposite.setFocus();
		}
	}

	private int getButtonIndex(Button btn) {
		return Arrays.asList(btn.getParent().getChildren()).indexOf(btn);
	}
}
