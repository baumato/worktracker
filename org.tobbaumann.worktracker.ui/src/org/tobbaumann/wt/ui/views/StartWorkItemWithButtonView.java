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

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
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
import org.tobbaumann.worktracker.ui.event.Events;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.domain.Activity;

public class StartWorkItemWithButtonView {

	private final WorkTrackingService service;
	private final IEventBroker eventBroker;
	private Composite buttonPanel;

	@Inject
	public StartWorkItemWithButtonView(WorkTrackingService service, IEventBroker eventBroker) {
		this.service = service;
		this.eventBroker = eventBroker;
	}

	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new FillLayout());
		this.buttonPanel = new Composite(parent, SWT.NONE);
		createMostUsedActivitiesButtons();
	}

	private void createMostUsedActivitiesButtons() {
		GridLayout layout = new GridLayout(1, true);
		buttonPanel.setLayout(layout);
		IObservableList mua = service.getMostUsedActivities(6);
		for (Object o : mua) {
			Activity a = (Activity) o;
			Button btn = new Button(buttonPanel, SWT.PUSH);
			btn.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			btn.setText(a.getName());
			startWorkItemOnButtonSelection(btn);
			makeButtonDropTarget(btn);
		}
	}

	private void startWorkItemOnButtonSelection(final Button btn) {
		btn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				service.startWorkItem(btn.getText(), 0);
				eventBroker.send(Events.START_WORK_ITEM, btn.getText());
			}
		});

	}

	private void makeButtonDropTarget(final Button btn) {
		DropTarget dt = new DropTarget(btn, DND.DROP_MOVE);
		dt.setTransfer(new Transfer[] { TextTransfer.getInstance() });
		dt.addDropListener(new DropTargetAdapter() {
			@Override
			public void drop(DropTargetEvent event) {
				btn.setText((String) event.data);
			}
		});
	}

	void fillButtonsWithMostUsedActivities() {
		Composite superParent = this.buttonPanel.getParent();
		this.buttonPanel.dispose();
		this.buttonPanel = new Composite(superParent, SWT.NONE);
		createMostUsedActivitiesButtons();
		superParent.layout(true, true);
	}

	@Focus
	public void requestFocus() {
		if (buttonPanel != null && !buttonPanel.isDisposed()) {
			buttonPanel.setFocus();
		}
	}
}
