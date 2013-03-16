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
package org.tobbaumann.worktracker.ui.theme;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;

public class ThemeSwitchHandler {

	@Execute
	public void switchTheme(@Named("commandparameter.themeid") String themeId, IThemeEngine engine) {
		System.out.println("Set theme to: " + themeId);
		engine.setTheme(themeId, true);
	}
}
