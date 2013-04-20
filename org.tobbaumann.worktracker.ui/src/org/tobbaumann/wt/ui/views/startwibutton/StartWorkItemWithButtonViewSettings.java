package org.tobbaumann.wt.ui.views.startwibutton;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.tobbaumann.wt.ui.preferences.WorkTrackerPreferences;
import org.tobbaumann.wt.ui.views.PreferencesComposite;

public class StartWorkItemWithButtonViewSettings extends PreferencesComposite {

	static final String FILL_BUTTON_PRESSED = "FillButtonPressed";

	private final PropertyChangeSupport pcs;

	private int numberOfButtonColumns;
	private int numberOfButtons;
	private List<String> buttonLabels;

	private Spinner spinnerNrBtnCols;
	private Spinner spinnerNrBtns;


	public StartWorkItemWithButtonViewSettings(Composite parent, WorkTrackerPreferences prefs) {
		super(parent, prefs);
		this.pcs = new PropertyChangeSupport(this);
		updatePreferenceFields();
		createSettingsPanel();
	}


	private void createSettingsPanel() {
		setLayout(new GridLayout(2, false));

		createLabel(this, prefs.getNumberOfButtonColumnsDisplayName());
		spinnerNrBtnCols = createSpinner(numberOfButtonColumns, prefs.getMaximumNumberOfButtonColumns());
		spinnerNrBtnCols.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				numberOfButtonColumns = spinnerNrBtnCols.getSelection();
			}
		});

		createLabel(this, prefs.getNumberOfButtonsDisplayName());
		spinnerNrBtns = createSpinner(numberOfButtons, prefs.getMaximumNumberOfButtons());
		spinnerNrBtns.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				numberOfButtons = spinnerNrBtns.getSelection();
			}
		});

		Button btnFill = new Button(this, SWT.PUSH);
		btnFill.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1));
		btnFill.setText("Fill buttons with most used activities");
		btnFill.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent se) {
				buttonLabels = Collections.emptyList();
				pcs.firePropertyChange(FILL_BUTTON_PRESSED, null, null);
			}
		});
	}

	private void createLabel(Composite settingsPanel, String labelText) {
		Label label = new Label(settingsPanel, SWT.LEFT);
		label.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		label.setText(labelText);
	}

	private Spinner createSpinner(int selection, int maxValue) {
		Spinner spinner = new Spinner(this, SWT.BORDER);
		spinner.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));
		spinner.setMinimum(1);
		spinner.setMaximum(maxValue);
		spinner.setIncrement(1);
		spinner.setPageIncrement(3);
		spinner.setSelection(selection);
		return spinner;
	}


	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}

	public void updatePreferenceFields() {
		this.numberOfButtonColumns = prefs.getNumberOfButtonColumns();
		this.numberOfButtons = prefs.getNumberOfButtons();
		this.buttonLabels = prefs.getWorkItemStartButtonLabels();
		if (this.spinnerNrBtnCols != null) {
			this.spinnerNrBtnCols.setSelection(numberOfButtonColumns);
		}
		if (this.spinnerNrBtns != null) {
			this.spinnerNrBtns.setSelection(numberOfButtons);
		}
	}


	@Override
	public void flushPreferences() {
		prefs.setNumberOfButtonColumns(numberOfButtonColumns);
		prefs.setNumberOfButtons(numberOfButtons);
		prefs.setWorkItemStartButtonLabels(buttonLabels);
	}
}
