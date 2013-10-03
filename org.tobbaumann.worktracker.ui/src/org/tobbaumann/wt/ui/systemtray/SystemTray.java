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
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.domain.WorkItem;
import org.tobbaumann.wt.ui.event.Events;
import org.tobbaumann.wt.ui.handlers.ExitHandler;
import org.tobbaumann.wt.ui.preferences.WorkTrackerPreferences;
import org.tobbaumann.wt.ui.views.startwibutton.ShortcutActivityNamesCreator;

@Creatable
@Singleton
public class SystemTray {

	private static final String REMINDER_TEXT = "Do you still working on \"%s\"?";

	private static final String TOOL_TIP_TEXT = "Working on \"%s\"";
	private static final String TRAY_ICON_IMAGE_NAME = "trayIcon";

	static {
		try {
			String strImgUrl = "platform:/plugin/org.tobbaumann.worktracker.ui/icons/pc.de/app-icon/finished-work.ico";
			URL imgUrl = URI.create(strImgUrl).toURL();
			ImageDescriptor id = ImageDescriptor.createFromURL(imgUrl);
			JFaceResources.getImageRegistry().put(TRAY_ICON_IMAGE_NAME, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private @Inject IWorkbench workbench;
	private @Inject WorkTrackingService service;
	private @Inject IEventBroker eventBroker;
	private @Inject ShortcutActivityNamesCreator shortcutNamesCreator;
	private Shell shell;
	private TrayItem trayItem;
	private ToolTip reminderToolTip;
	private final Job reminderJob;
	private volatile String currentTooltipText = null;
	private Point shellLocation;
	private Point shellSize;

	private boolean useReminder;
	private long remindFreqInMillis;

	@Inject
	public SystemTray(Shell shell) {
		this.shell = shell;
		this.reminderJob = new RemindJob();
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
		registerTrayItemMenuDetectListener();
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

	private void registerTrayItemMenuDetectListener() {
		trayItem.addMenuDetectListener(new MenuDetectListener() {
			@Override
			public void menuDetected(MenuDetectEvent e) {
				createAndShowMenu();
			}
		});
	}

	private void createAndShowMenu() {
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

		createMenuItemsFromMostUsedActivites(menu);

		menuItem = new MenuItem(menu, SWT.SEPARATOR);

		menuItem = new MenuItem(menu, SWT.PUSH);
		menuItem.setText("Exit");
		menuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ExitHandler exitHandler = new ExitHandler();
				exitHandler.exit(workbench);
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

	private void createMenuItemsFromMostUsedActivites(final Menu menu) {
		List<String> names = shortcutNamesCreator.create();
		for (String name : names) {
			final MenuItem menuItem = new MenuItem(menu, SWT.PUSH);
			menuItem.setText(name);
			menuItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					service.startWorkItem(menuItem.getText(), 0);
					eventBroker.send(Events.START_WORK_ITEM, service.getActiveWorkItem());
				}
			});
		}
	}

	@Inject
	@org.eclipse.e4.core.di.annotations.Optional
	public void updateUseReminder(
			@Preference(value = WorkTrackerPreferences.USE_REMINDER) boolean useReminder) {
		if (this.useReminder != useReminder) {
			this.useReminder = useReminder;
			remindPeriodically();
		}
	}

	@Inject
	@org.eclipse.e4.core.di.annotations.Optional
	public void updateRemindFrequency(
			@Preference(value = WorkTrackerPreferences.REMIND_FREQUENCY) long remindFrequency) {
		if (this.remindFreqInMillis != remindFrequency) {
			this.remindFreqInMillis = remindFrequency;
			remindPeriodically();
		}
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

	private void remindPeriodically() {
		reminderJob.cancel();
		if (useReminder) {
			reminderJob.schedule(remindFreqInMillis);
		}
	}


	private void switchApplicationState() {
		if (shell.isVisible()) {
			pushApplicationToTray();
		} else {
			popApplicationFromTray();
		}
	}

	private void pushApplicationToTray() {
		shellLocation = shell.getLocation();
		shellSize = shell.getSize();
		shell.setVisible(false);
	}

	private void popApplicationFromTray() {
		shell.setVisible(true);
		shell.setLocation(shellLocation);
		shell.setSize(shellSize);
		shell.setActive();
		shell.setFocus();
		shell.setMinimized(false);
	}

	private void checkIfCanceled(IProgressMonitor monitor) {
		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
	}

	private void showReminder() {
		reminderToolTip.setVisible(true);
	}

	private String createShowHideText() {
		return shell.isVisible() ? String.format("Push %s to tray",
				Display.getAppName()) : String.format("Bring %s to front",
				Display.getAppName());
	}


	/**
	 *
	 * @author tobbaumann
	 *
	 */
	private final class RemindJob extends Job {

		private RemindJob() {
			super("Show Reminder Periodically");
		}

		@Override
		protected IStatus run(final IProgressMonitor monitor) {
			shell.getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					try {
						if (!isNullOrEmpty(currentTooltipText)) {
							checkIfCanceled(monitor);
							showReminder();
							checkIfCanceled(monitor);
							schedule(remindFreqInMillis);
						}
					} catch (OperationCanceledException e) {
						return;
					}
				}
			});
			return Status.OK_STATUS;
		}
	}
}
