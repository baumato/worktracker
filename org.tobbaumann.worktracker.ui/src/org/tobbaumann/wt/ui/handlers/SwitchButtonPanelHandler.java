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

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.tobbaumann.wt.ui.views.Switchable;

public class SwitchButtonPanelHandler {

	@Execute
	public void switchPanel(MPart part, @Optional @Named("commandparameter.keyboardstart") String strStartedByShortcut) {
		Switchable view = (Switchable) part.getObject();
		view.switchPanel();
		if (Boolean.valueOf(strStartedByShortcut)) {
			view.switchToolItemState();
		}
	}
}
