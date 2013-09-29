package org.tobbaumann.wt.ui.views.wisummary;

import java.util.Date;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.tobbaumann.wt.domain.Activity;
import org.tobbaumann.wt.domain.DomainFactory;
import org.tobbaumann.wt.domain.WorkItem;
import org.tobbaumann.wt.domain.WorkItemSummaries;
import org.tobbaumann.wt.domain.WorkItemSummary;

public class WeMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		WorkItem w1 = DomainFactory.eINSTANCE.createWorkItem();
		Activity a1 = DomainFactory.eINSTANCE.createActivity();
		a1.setName("w1");
		w1.setActivity(a1);
		w1.setStart(new Date(2013, 9, 28, 17, 0));
		w1.setEndDate(new Date(2013, 9, 28, 18, 30));
		System.out.println(w1.getDuration());

		WorkItemSummary ws = DomainFactory.eINSTANCE.createWorkItemSummary();
		ws.getWorkItems().add(w1);
		ws.getWorkItems().add(EcoreUtil.copy(w1));
		System.out.println(ws.getWorkItems().size());

		System.out.println(ws.getSumOfDurations());

		WorkItemSummaries wss = DomainFactory.eINSTANCE.createWorkItemSummaries();
		wss.getWorkItemSummaries().add(ws);
		wss.getWorkItemSummaries().add(EcoreUtil.copy(ws));
		System.out.println(wss.getWorkItemSummaries().size());

		System.out.println(wss.getSumOfDurations());

		System.out.println(wss.computeDurationRatioInPercent(ws));
		System.out.println(wss.computeDurationRatio(ws, 480));




	}

}
