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
package org.tobbaumann.wt.ui.handlers;

import static com.google.common.base.Objects.firstNonNull;
import static com.google.common.base.Strings.isNullOrEmpty;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.core.WorkTrackingService.CreationResult;
import org.tobbaumann.wt.core.WorkTrackingService.OperationCanceledException;

import com.google.common.base.Throwables;
import com.google.common.collect.Ranges;

public class CreateFakeDataHandler {

	private WorkTrackingService service;
	private Shell shell;

	@Inject
	public CreateFakeDataHandler(WorkTrackingService service, Shell shell) {
		this.service = service;
		this.shell = shell;
	}

	@Execute
	public void execute() {
		final String errMsg = "Errors occurred during creating fake data.";
		try {
			final Integer numberOfDays = askForNumberOfDays();
			if (numberOfDays == null) {
				return;
			}
			final CreationResult[] resultExchange = new CreationResult[1];
			ProgressMonitorDialog dlg = new ProgressMonitorDialog(shell);
			dlg.run(true, true, new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException,
						InterruptedException {
					try {
						resultExchange[0] = service.createFakeData(numberOfDays, monitor);
					} catch (OperationCanceledException e) {
						throw new InterruptedException(Throwables.getStackTraceAsString(e));
					}
				}
			});
			handleImportResult(shell, errMsg, resultExchange[0]);
		} catch (InterruptedException e) {
			MessageDialog.openInformation(shell, "Aborted", "Creating fake data has been aborted. No data has been changed.");
		} catch (Exception e) {
			Throwable cause = firstNonNull(e.getCause(), e);
			ErrorDialog.openError(shell, "Error", errMsg, new Status(IStatus.ERROR, "WorkTracker",
					cause.getMessage(), cause));
		}
	}

	private Integer askForNumberOfDays() {
		InputDialog dialog = new InputDialog(shell, "Number of days",
				"How many days should be faked?", "15", new IInputValidator() {
					@Override
					public String isValid(String value) {
						if (isNullOrEmpty(value)) {
							return "Please enter the number of days that you want to fake.";
						}
						if (!value.matches("[\\d]*")) {
							return "Please enter a number.";
						}
						if (!Ranges.closed(1, 3650).contains(Integer.valueOf(value))) {
							return "Please enter a number between 1..3650.";
						}
						return null;
					}
				});
		dialog.setBlockOnOpen(true);
		return dialog.open() == Window.OK ? Integer.valueOf(dialog.getValue()) : null;
	}

	private void handleImportResult(final Shell shell, final String errMsg, final CreationResult ir) {
		shell.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (!ir.errors.isEmpty()) {
					ErrorDialog.openError(shell, errMsg, errMsg, createErrorStatus(errMsg, ir));
				} else {
					MessageDialog.openInformation(shell, "Import finished.", ir.toString());
				}
			}
		});
	}

	private MultiStatus createErrorStatus(String errMsg, final CreationResult ir) {
		return new MultiStatus(ImportOldWorkTrackerDataHandler.class
				.getSimpleName(), 0, ir.errors.toArray(new IStatus[0]),
				errMsg, null);
	}
}
