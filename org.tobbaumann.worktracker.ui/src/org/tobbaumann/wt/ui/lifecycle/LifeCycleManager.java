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
package org.tobbaumann.wt.ui.lifecycle;

import org.eclipse.core.internal.registry.osgi.OSGIUtils;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.ui.systemtray.SystemTray;

public class LifeCycleManager {

	@PostContextCreate
	public void postContextCreate(final IEventBroker eventBroker, final IEclipseContext ec) {
		eventBroker.subscribe(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE, new EventHandler() {
			@Override
			public void handleEvent(Event event) {
				try {
					setWorkTrackerSystemTray(ec);
					askUserToEndActiveWorkItemOnShellClose();
				} finally {
					eventBroker.unsubscribe(this);
				}
			}
		});
	}

	private void askUserToEndActiveWorkItemOnShellClose() {
		Display.getCurrent().getActiveShell().addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				askUserToEndActiveWorkItem((Shell) e.getSource());
			}
		});
	}

	private void askUserToEndActiveWorkItem(Shell shell) {
		WorkTrackingService service = getWorkTrackingService();
		if (service.getActiveWorkItem().isPresent()) {
			String name = service.getActiveWorkItem().get().getActivityName();
			boolean ok = MessageDialog.openQuestion(
				shell,
				"End work item?",
				String.format("Do you want to end your current active work item '%s'?", name));
			if (ok) {
				service.endActiveWorkItem();
			}
		}
	}

	private WorkTrackingService getWorkTrackingService() {
		BundleContext wtCoreBundleContext = OSGIUtils
			.getDefault()
			.getBundle("org.tobbaumann.worktracker.core")
			.getBundleContext();
		WorkTrackingService service = wtCoreBundleContext.getService(wtCoreBundleContext
			.getServiceReference(WorkTrackingService.class));
		return service;
	}

	private void setWorkTrackerSystemTray(final IEclipseContext ec) {
		IEclipseContext context = EclipseContextFactory.create();
		context.set(Shell.class.getName(), Display.getCurrent().getActiveShell());
		ContextInjectionFactory.make(SystemTray.class, context, ec);
	}
}
