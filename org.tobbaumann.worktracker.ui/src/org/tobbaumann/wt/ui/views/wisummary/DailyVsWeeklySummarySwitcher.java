package org.tobbaumann.wt.ui.views.wisummary;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

public class DailyVsWeeklySummarySwitcher {

	@Execute
	public void switchSummary(MPart part) {
		WorkItemSummaryView view = (WorkItemSummaryView) part.getObject();
		view.switchSummary();
	}

}
