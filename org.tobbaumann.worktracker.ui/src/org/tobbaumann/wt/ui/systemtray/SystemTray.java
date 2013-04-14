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
package org.tobbaumann.wt.ui.systemtray;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.net.URI;
import java.net.URL;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.tobbaumann.wt.domain.WorkItem;
import org.tobbaumann.wt.ui.event.Events;
import org.tobbaumann.wt.ui.preferences.Preferences;

@Creatable
@Singleton
public class SystemTray {

	private static final String REMINDER_TEXT = "Do you still working on \"%s\"?";
	private static final String TOOL_TIP_TEXT = "Working on \"%s\"";
	private static final String TRAY_ICON_IMAGE_NAME = "trayIcon";

	static {
		try {
			String strImgUrl = "platform:/plugin/org.tobbaumann.worktracker.ui/icons/clocks/clock_16x16.png";
			URL imgUrl = URI.create(strImgUrl).toURL();
			ImageDescriptor id = ImageDescriptor.createFromURL(imgUrl);
			JFaceResources.getImageRegistry().put(TRAY_ICON_IMAGE_NAME, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private @Inject	Shell shell;
	private TrayItem trayItem;
	private ToolTip reminderToolTip;
	private Job reminderJob = null;
	private volatile String currentTooltipText = null;

	@Inject
	public SystemTray(Shell shell) {
		this.shell = shell;
		setupTray();
	}

	private void setupTray() {
		Display display = shell.getDisplay();
		Tray tray = display.getSystemTray();
		if (tray == null) {
			return;
		}
		trayItem = createTrayItem(tray);
		reminderToolTip = createReminderToolTip();
		switchApplicationStateOnMinimize();
		switchApplicationStateOnSelection();
		createTrayItemMenu();
	}

	private TrayItem createTrayItem(Tray tray) {
		TrayItem trayItem = new TrayItem(tray, SWT.NONE);
		trayItem.setText(Display.getAppName());
		trayItem.setImage(JFaceResources.getImage(TRAY_ICON_IMAGE_NAME));
		return trayItem;
	}

	private ToolTip createReminderToolTip() {
		final ToolTip tip = new ToolTip(shell, SWT.BALLOON
				| SWT.ICON_INFORMATION);
		tip.setMessage("If not, you may want to update your current activity.");
		trayItem.setToolTip(tip);
		return tip;
	}

	private void switchApplicationStateOnMinimize() {
		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellIconified(ShellEvent e) {
				switchApplicationState();
			}
		});
	}

	private void switchApplicationStateOnSelection() {
		trayItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				switchApplicationState();
			}
		});
	}

	private void remindPeriodically() {
		if (reminderJob != null) {
			return;
		}
		reminderJob = new Job("Show Reminder Periodically") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				shell.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						if (!useReminder()
								|| isNullOrEmpty(currentTooltipText)) {
							schedule(10000);
							return;
						}
						shell.getDisplay().syncExec(new Runnable() {
							@Override
							public void run() {
								showReminder();
							}
						});
						schedule(getRemindFrequencyInMillis());
					}
				});
				return Status.OK_STATUS;
			}
		};
		reminderJob.schedule(getRemindFrequencyInMillis());
	}

	private long getRemindFrequencyInMillis() {
		return ConfigurationScope.INSTANCE.getNode(Preferences.REMINDER)
				.getLong(Preferences.REMIND_FREQUENCY,
						Preferences.REMIND_FREQUENCY_DEFAULT);
	}

	private void createTrayItemMenu() {
		trayItem.addMenuDetectListener(new MenuDetectListener() {
			@Override
			public void menuDetected(MenuDetectEvent e) {
				final Menu menu = new Menu(shell, SWT.POP_UP);
				MenuItem menuItem = new MenuItem(menu, SWT.PUSH);
				menuItem.setText(createShowHideText());
				menuItem.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						switchApplicationState();
					}
				});

				if (!isNullOrEmpty(currentTooltipText)) {
					menuItem = new MenuItem(menu, SWT.PUSH);
					menuItem.setText("Show reminder");
					menuItem.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							showReminder();
						}
					});
				}

				menuItem = new MenuItem(menu, SWT.SEPARATOR);

				menuItem = new MenuItem(menu, SWT.PUSH);
				menuItem.setText("Exit");
				menuItem.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO call ExitHandler
						shell.close();
					}
				});

				menu.setVisible(true);
				while (!menu.isDisposed() && menu.isVisible()) {
					if (!menu.getDisplay().readAndDispatch()) {
						menu.getDisplay().sleep();
					}
				}
				menu.dispose();
			}
		});
	}


	@Inject @org.eclipse.e4.core.di.annotations.Optional
	void workItemStarted(
			@UIEventTopic(Events.START_WORK_ITEM) com.google.common.base.Optional<WorkItem> owi) {
		if (!owi.isPresent()) {
			return;
		}
		WorkItem wi = owi.get();
		currentTooltipText = String.format(REMINDER_TEXT, wi.getActivityName());
		reminderToolTip.setText(currentTooltipText);
		trayItem.setToolTipText(String.format(TOOL_TIP_TEXT, wi.getActivityName()));
		remindPeriodically();
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

	private boolean useReminder() {
		return ConfigurationScope.INSTANCE.getNode(Preferences.REMINDER)
				.getBoolean(Preferences.USE_REMINDER,
						Preferences.USE_REMINDER_DEFAULT);
	}

	private void showReminder() {
		reminderToolTip.setVisible(true);
	}

	private String createShowHideText() {
		return shell.isVisible() ? String.format("Push %s to tray",
				Display.getAppName()) : String.format("Bring %s to front",
				Display.getAppName());
	}
}
