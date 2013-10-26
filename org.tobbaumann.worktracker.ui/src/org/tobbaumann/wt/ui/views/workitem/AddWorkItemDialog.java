package org.tobbaumann.wt.ui.views.workitem;

import static com.google.common.base.Strings.emptyToNull;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.base.Strings.nullToEmpty;

import java.util.Calendar;
import java.util.Date;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.tobbaumann.wt.domain.Activity;
import org.tobbaumann.wt.domain.DomainFactory;
import org.tobbaumann.wt.domain.WorkItem;

public class AddWorkItemDialog extends Dialog {

	private Text txtActivity;
	private DateTime dtStart;
	private DateTime dtEnd;
	private Text txtDescription;

	private boolean shouldValidate = false;
	private WorkItem workItem = DomainFactory.eINSTANCE.createWorkItem();
	private Button btnStillRunning;
	private Label lblErrMsg;
	private Color defaultLblColor;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public AddWorkItemDialog(Shell parentShell, WorkItem wi) {
		super(parentShell);
		this.workItem = EcoreUtil.copy(wi);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Add work item");
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.numColumns = 3;

		Label lblActivity = new Label(container, SWT.NONE);
		lblActivity.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		lblActivity.setText("Activity");

		txtActivity = new Text(container, SWT.BORDER);
		txtActivity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		txtActivity.setText(workItem.getActivityName());
		txtActivity.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				Activity activity = DomainFactory.eINSTANCE.createActivity();
				activity.setName(txtActivity.getText());
				workItem.setActivity(activity);
				checkIfDialogIsValid();
			}
		});

		Label lblStart = new Label(container, SWT.NONE);
		lblStart.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		lblStart.setText("Start");

		dtStart = new DateTime(container, SWT.BORDER | SWT.TIME | SWT.SHORT);
		dtStart.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(workItem.getStart());
		dtStart.setTime(startCal.get(Calendar.HOUR_OF_DAY), Calendar.MINUTE, Calendar.SECOND);
		dtStart.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				Calendar cal = dateTimeToCalendar(dtStart);
				workItem.setStart(cal.getTime());
			}
		});

		Label lblEnd = new Label(container, SWT.NONE);
		lblEnd.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		lblEnd.setText("End");

		dtEnd = new DateTime(container, SWT.BORDER | SWT.TIME | SWT.SHORT);
		dtEnd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(workItem.getEnd());
		dtEnd.setTime(endCal.get(Calendar.HOUR_OF_DAY), endCal.get(Calendar.MINUTE), endCal.get(Calendar.SECOND));
		dtEnd.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				Calendar cal = dateTimeToCalendar(dtEnd);
				workItem.setEndDate(cal.getTime());
			}
		});

		btnStillRunning = new Button(container, SWT.CHECK);
		btnStillRunning.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				dtEnd.setEnabled(!btnStillRunning.getSelection());
			}
		});
		btnStillRunning.setText("Still running");
		btnStillRunning.setSelection(workItem.getEndDate() == null);
		dtEnd.setEnabled(!btnStillRunning.getSelection());

		Label lblDescription = new Label(container, SWT.NONE);
		lblDescription.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		lblDescription.setText("Description");

		txtDescription = new Text(container, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		txtDescription.setText(nullToEmpty(workItem.getDescription()));
		txtDescription.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				workItem.setDescription(txtDescription.getText());
			}
		});
		txtDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		lblErrMsg = new Label(container, SWT.NONE);
		lblErrMsg.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1));
		defaultLblColor = lblErrMsg.getForeground();
		return container;
	}

	private Calendar dateTimeToCalendar(DateTime dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(workItem.getStart());
		cal.set(Calendar.HOUR_OF_DAY, dt.getHours());
		cal.set(Calendar.MINUTE, dt.getMinutes());
		cal.set(Calendar.SECOND, dt.getSeconds());
		return cal;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}

	@Override
	protected void okPressed() {
		shouldValidate = true;
		if (checkIfDialogIsValid()) {
			if (btnStillRunning.getSelection()) {
				workItem.setEndDate(null);
			}
			super.okPressed();
		}
	}

	private boolean checkIfDialogIsValid() {
		if (!shouldValidate) {
			return true;
		}
		if (isNullOrEmpty(workItem.getActivityName())) {
			//lblErrMsg.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
			lblErrMsg.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
			lblErrMsg.setText("The activity should not be empty.");
			return false;
		}
		lblErrMsg.setText("Please press OK to add the work item.");
		lblErrMsg.setForeground(defaultLblColor);
		return true;
	}

	public DialogResult openDialog() {
		if (open() == Window.OK) {
			return new DialogResult(workItem);
		}
		return null;
	}

	@Override
	public boolean close() {
		this.defaultLblColor = null;
		return super.close();
	}



	/**
	 *
	 * @author tobbaumann
	 *
	 */
	public static class DialogResult {
		private final WorkItem workItem;

		private DialogResult(WorkItem wi) {
			this.workItem = wi;
		}

		public Date getStart() {
			return workItem.getStart();
		}

		public Date getEnd() {
			return workItem.getEndDate();
		}

		public boolean shouldStillBeRunning() {
			return workItem.getEndDate() == null;
		}

		public String getActivityName() {
			return workItem.getActivityName();
		}

		public String getDescription() {
			return emptyToNull(workItem.getDescription());
		}

	}
}
