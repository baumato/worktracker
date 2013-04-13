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
package org.tobbaumann.wt.ui.preferences;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;

public class OpenPreferencesHandler {

	public OpenPreferencesHandler() {
	}

	@Execute
	public void openPreferences(Shell shell) {
		PreferencesDialog dlg = new PreferencesDialog(shell);
		dlg.open();
	}
}
