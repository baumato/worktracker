package org.tobbaumann.wt.ui.views.startwibutton;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

public class StartWorkItemWithShortcutHandler {

	@Execute
	public void execute(MApplication appl, EModelService service,
			@Named("commandparameter.keystroke") String buttonNumber) {
		MPart part = (MPart) service.find("startworkitemwithbuttonsview", appl);
		StartWorkItemWithButtonView view = (StartWorkItemWithButtonView) part.getObject();
		int buttonIndex = Integer.valueOf(buttonNumber)-1;
		view.startWorkItem(buttonIndex);
	}
}