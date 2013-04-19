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

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.domain.Activity;
import org.tobbaumann.wt.ui.event.Events;
import org.tobbaumann.wt.ui.views.SwitchComposite;
import org.tobbaumann.wt.ui.views.Switchable;

public class StartWorkItemWithButtonView implements Switchable {

	private static final String BTN_DATA_KEY_ACTIVITY_NAME = "activityName";

	private final Logger logger;
	private final WorkTrackingService service;
	private final IEventBroker eventBroker;

	private @Inject MPart part;
	private SwitchComposite switchComposite;
	private Composite settingsPanel;
	private Composite buttonPanel;
	private Composite buttonPanelContent;

	@Inject
	public StartWorkItemWithButtonView(WorkTrackingService service, IEventBroker eventBroker,
			Logger logger) {
		this.service = service;
		this.eventBroker = eventBroker;
		this.logger = logger;
	}

	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new FillLayout());
		switchComposite = new SwitchComposite(parent);
		this.buttonPanel = new Composite(switchComposite, SWT.NONE);
		createButtonPanel();
		this.settingsPanel = new Composite(switchComposite, SWT.NONE);
		createSettingsPanel();
		switchComposite.setTopControl(determineTopControlFromToolItemState());
	}

	@Override
	public void switchPanel() {
		Preference.flushAllPreferences();
		switchComposite.switchActiveControl();
		if (switchComposite.getTopControl() == buttonPanel) {
			this.buttonPanelContent.dispose();
			createButtonPanel();
			switchComposite.layout(true, true);
		}
	}

	@Override
	public void switchToolItemState() {
		getSettingsToolItem().setSelected(!getSettingsToolItem().isSelected());
	}

	private void createButtonPanel() {
		try {
			Preferences prefs = Preference.BUTTON_NAMES.getPreferencesStore();;
			if (buttonsAreUserDefined(prefs)) {
				List<String> activityNames = newArrayList();
				for (String key : prefs.keys()) {
					activityNames.add(prefs.get(key, ""));
				}
				createButtonPanel(activityNames);
			} else {
				createMostUsedActivitiesButtons();
			}
		} catch (BackingStoreException e) {
			logger.error(e, "Error during reading preferences.");
			createMostUsedActivitiesButtons();
		}
	}

	private boolean buttonsAreUserDefined(Preferences prefs) throws BackingStoreException {
		return prefs != null && prefs.keys().length > 0;
	}

	private void createMostUsedActivitiesButtons() {
		List<Activity> activities = service.getMostUsedActivities(readPreference(Preference.NUMBER_OF_BUTTONS));
		List<String> mua = newArrayList();
		for (Activity a : activities) {
			mua.add(a.getName());
		}
		createButtonPanel(mua);
	}

	private void createButtonPanel(List<String> activityNames) {
		buttonPanel.setLayout(new FillLayout());
		buttonPanelContent = new Composite(buttonPanel, SWT.NONE);
		GridLayout layout = new GridLayout(readPreference(Preference.NUMBER_OF_BUTTONS_COLUMNS), true);
		buttonPanelContent.setLayout(layout);
		int numberOfBtns = readPreference(Preference.NUMBER_OF_BUTTONS);
		for (int i = 0; i < numberOfBtns; i++) {
			int buttonNumber = i+1;
			if (i < activityNames.size()) {
				createButton(buttonPanelContent, activityNames.get(i), buttonNumber);
			} else {
				createButton(buttonPanelContent, "", buttonNumber);
			}
		}
	}

	private void createSettingsPanel() {
		settingsPanel.setLayout(new GridLayout(2, false));
		createLabel(settingsPanel, Preference.NUMBER_OF_BUTTONS_COLUMNS.name);
		createSpinner(Preference.NUMBER_OF_BUTTONS_COLUMNS);
		createLabel(settingsPanel, Preference.NUMBER_OF_BUTTONS.name);
		createSpinner(Preference.NUMBER_OF_BUTTONS);

		Button btnFill = new Button(settingsPanel, SWT.PUSH);
		btnFill.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1));
		btnFill.setText("Fill buttons with most used activities");
		btnFill.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent se) {
				Preferences prefs = Preference.BUTTON_NAMES.getPreferencesStore();
				if (prefs != null) {
					try {
						prefs.removeNode();
					} catch (BackingStoreException e) {
						logger.error(e, "Error during removing node: " + prefs.name());
					}
				}
				switchToolItemState();
				switchPanel();
			}
		});
	}

	private void createSpinner(final Preference p) {
		final Spinner spinner = new Spinner(settingsPanel, SWT.BORDER);
		spinner.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));
		spinner.setMinimum(1);
		spinner.setMaximum(p.maximumValue);
		spinner.setIncrement(1);
		spinner.setPageIncrement(3);
		Integer selection = readPreference(p);
		spinner.setSelection(selection);
		spinner.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final Preferences preferences = p.getPreferencesStore();
				preferences.put(p.id, spinner.getText());
			}
		});
	}

	private int readPreference(Preference p) {
		final Preferences preferences = p.getPreferencesStore();
		return Integer.valueOf(preferences.get(p.id, String.valueOf(p.defaultValue)));
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

	private void createLabel(Composite settingsPanel, String labelText) {
		Label label = new Label(settingsPanel, SWT.LEFT);
		label.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		label.setText(labelText);
	}

	private Composite determineTopControlFromToolItemState() {
		MToolItem item = getSettingsToolItem();
		return item.isSelected()
				? settingsPanel
				: buttonPanel;
	}

	// TODO how get the MToolItem in a better and more robust way?
	private MToolItem getSettingsToolItem() {
		return (MToolItem) part.getToolbar().getChildren().get(0);
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
		Preferences prefs = Preference.BUTTON_NAMES.getPreferencesStore();
		Control[] buttonList = btnParent.getChildren();
		for (int buttonIndex = 0; buttonIndex < buttonList.length; buttonIndex++) {
			Control c = buttonList[buttonIndex];
			if (c instanceof Button) {
				Button btn = (Button) c;
				prefs.put(String.valueOf(buttonIndex), getActivityNameFromButton(btn));
			}
		}
		Preference.flushAllPreferences();
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

	/**
	 *
	 * @author tobbaumann
	 *
	 */
	private enum Preference {
		NUMBER_OF_BUTTONS("NUMBER_OF_BUTTONS", "Number Of Buttons", 6, 20, null),
		NUMBER_OF_BUTTONS_COLUMNS("NUMBER_OF_BUTTONS_COLUMNS", "Number Of Button Columns", 1, 6, null),
		BUTTON_NAMES("BUTTON_NAMES", "Button Names", -1, -1, "BUTTON_NAMES");

		private static final String MAIN_NODE_PATH = StartWorkItemWithButtonView.class.getSimpleName();
		final String id;
		final String name;
		final int defaultValue;
		final int maximumValue;
		final String additionalNodePath;

		private Preference(String id, String name, int defaultValue, int maximumValue, String additionalNodePath) {
			this.id = id;
			this.name = name;
			this.defaultValue = defaultValue;
			this.maximumValue = maximumValue;
			this.additionalNodePath = additionalNodePath;
		}

		Preferences getPreferencesStore() {
			String path = additionalNodePath == null
					? MAIN_NODE_PATH
					: MAIN_NODE_PATH + "/" + additionalNodePath;
			return ConfigurationScope.INSTANCE.getNode(path);
		}

		static void flushAllPreferences() {
			try {
				ConfigurationScope.INSTANCE.getNode(MAIN_NODE_PATH).flush();
			} catch (BackingStoreException e) {
				e.printStackTrace();
			}
		}
	}
}
