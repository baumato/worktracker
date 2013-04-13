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

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.core.WorkTrackingService.CreationResult;
import org.tobbaumann.wt.core.WorkTrackingService.OperationCanceledException;

import com.google.common.base.Throwables;

public class ImportOldWorkTrackerDataHandler {

	private final WorkTrackingService service;
	private final IEventBroker eventBroker;

	@Inject
	public ImportOldWorkTrackerDataHandler(WorkTrackingService service, IEventBroker eventBroker) {
		this.service = service;
		this.eventBroker = eventBroker;
	}

	@Execute
	public void execute(final Shell shell) {
		final String errMsg = "Errors occurred during importing data.";
		try {
			DirectoryDialog dialog = new DirectoryDialog(shell);
			dialog.setText("Choose directory");
			dialog.setMessage("Select directory containing WorkTracker data.");
			final String path = dialog.open();
			if (path == null) {
				return;
			}
			final CreationResult[] resultExchange = new CreationResult[1];
			ProgressMonitorDialog dlg = new ProgressMonitorDialog(shell);
			dlg.run(true, true, new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException,
						InterruptedException {
					try {
						resultExchange[0] = service.importData(path, monitor);
					} catch (OperationCanceledException e) {
						throw new InterruptedException(Throwables.getStackTraceAsString(e));
					}
				}
			});
			handleImportResult(shell, errMsg, resultExchange[0]);
		} catch (InterruptedException e) {
			MessageDialog.openInformation(shell, "Aborted", "Import has been aborted. No data has been changed.");
		} catch (Exception e) {
			ErrorDialog.openError(shell, "Error", errMsg,
					new Status(IStatus.ERROR, "WorkTracker", firstNonNull(e.getCause(), e).getMessage(), firstNonNull(e.getCause(), e)));
		}
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
