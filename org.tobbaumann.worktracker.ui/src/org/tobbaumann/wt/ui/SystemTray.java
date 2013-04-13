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
package org.tobbaumann.wt.ui;

import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;
import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;

public class SystemTray {

	private static final String TRAY_ICON_IMAGE_NAME = "trayIcon";
	static {
		putTrayImageToRegistry();
	}

	private final Shell shell;

	private SystemTray(Shell shell) {
		this.shell = shell;
	}

	private static void putTrayImageToRegistry() {
		try {
			String strImgUrl = "platform:/plugin/org.tobbaumann.worktracker.ui/icons/clocks/clock_16x16.png";
			URL imgUrl = URI.create(strImgUrl).toURL();
			ImageDescriptor id = ImageDescriptor.createFromURL(imgUrl);
			JFaceResources.getImageRegistry().put(TRAY_ICON_IMAGE_NAME, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void setupTray(Shell shell) {
		SystemTray tray = new SystemTray(checkNotNull(shell, "Given shell must not be null."));
		tray.setupTray();
	}

	private void switchApplicationState() {
		if (shell.isVisible()) {
			pushApplicationToTray();
		} else {
			popApplicationFromTray();
		}
	}

	private void pushApplicationToTray() {
		shell.setVisible(false);
	}

	private void popApplicationFromTray() {
		shell.setVisible(true);
		shell.setActive();
		shell.setFocus();
		shell.setMinimized(false);
	}

	private void setupTray() {
		Display display = shell.getDisplay();
		Tray tray = display.getSystemTray();
		if (tray == null) {
			return;
		}
		TrayItem trayItem = new TrayItem(tray, SWT.NONE);
		trayItem.setText("WorkTracker");
		trayItem.setImage(JFaceResources.getImage(TRAY_ICON_IMAGE_NAME));
		switchApplicationStateOnSelection(trayItem);
		switchApplicationStateOnMinimize();
	}

	private void switchApplicationStateOnSelection(TrayItem trayItem) {
		trayItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				switchApplicationState();
			}
		});
	}

	private void switchApplicationStateOnMinimize() {
		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellIconified(ShellEvent e) {
				switchApplicationState();
			}
		});
	}
}
