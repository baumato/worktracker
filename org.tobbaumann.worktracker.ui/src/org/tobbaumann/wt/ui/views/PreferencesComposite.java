package org.tobbaumann.wt.ui.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.tobbaumann.wt.ui.preferences.WorkTrackerPreferences;

public abstract class PreferencesComposite extends Composite {

	protected WorkTrackerPreferences prefs;

	public PreferencesComposite(Composite parent, WorkTrackerPreferences prefs) {
		super(parent, SWT.NONE);
		this.prefs = prefs;
	}

	public abstract void updatePreferenceFields();
	public abstract void flushPreferences();

}
