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
import org.tobbaumann.worktracker.ui.event.Events;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.core.WorkTrackingService.ImportResult;
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
			ProgressMonitorDialog dlg = new ProgressMonitorDialog(shell);
			dlg.run(false, true, new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException,
						InterruptedException {
					eventBroker.send(Events.START_IMPORT, Events.START_IMPORT+"Blub");
					ImportResult ir = null;
					try {
						ir = service.importData(path, monitor);
					} catch (OperationCanceledException e) {
						throw new InterruptedException(Throwables.getStackTraceAsString(e));
					}
					eventBroker.send(Events.END_IMPORT, Events.END_IMPORT+"diwub");
					handleImportResult(shell, errMsg, ir);
				}
			});
		} catch (InterruptedException e) {
			ErrorDialog.openError(shell, "Aborted", "Import has been aborted", Status.CANCEL_STATUS);
		} catch (Exception e) {
			ErrorDialog.openError(shell, "Error", errMsg,
					new Status(IStatus.ERROR, "WorkTracker", firstNonNull(e.getCause(), e).getMessage(), firstNonNull(e.getCause(), e)));
		}
	}

	private void handleImportResult(final Shell shell, final String errMsg, final ImportResult ir) {
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

	private MultiStatus createErrorStatus(String errMsg, final ImportResult ir) {
		return new MultiStatus(ImportOldWorkTrackerDataHandler.class
				.getSimpleName(), 0, ir.errors.toArray(new IStatus[0]),
				errMsg, null);
	}
}
