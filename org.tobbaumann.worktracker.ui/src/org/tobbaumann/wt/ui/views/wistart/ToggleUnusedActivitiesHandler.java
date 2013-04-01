 
package org.tobbaumann.wt.ui.views.wistart;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MToolItem;

public class ToggleUnusedActivitiesHandler {
	
	@Execute
	public void toggleUnusedActivities(MPart part, MToolItem item) {
		StartWorkItemView view = (StartWorkItemView) part.getObject();
		view.toggleUnusedActivities();
	}
		
}