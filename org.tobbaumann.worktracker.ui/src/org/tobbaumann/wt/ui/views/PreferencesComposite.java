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
