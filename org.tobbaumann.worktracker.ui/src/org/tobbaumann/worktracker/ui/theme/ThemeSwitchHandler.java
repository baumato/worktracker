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