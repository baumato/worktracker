/*******************************************************************************
 * Copyright (c) 2012 tobbaumann.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     tobbaumann - initial API and implementation
 ******************************************************************************/
package org.baumato.wt.ui.handlers;

import javax.inject.Inject;

import org.baumato.wt.core.ActivityRepository;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class OpenPreferencesHandler {

	@Inject
	ActivityRepository repo;
	
	@Execute
	public void openPreferences(Shell shell) {
		MessageDialog.openInformation(shell, "Preferences", "Open Preferences");
		
		repo.getActivityDates().add("2013-02-12");
	}
}
