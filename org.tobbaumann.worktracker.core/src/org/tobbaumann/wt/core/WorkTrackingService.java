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
package org.tobbaumann.wt.core;

import java.util.Date;
import java.util.List;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.tobbaumann.wt.domain.Activity;
import org.tobbaumann.wt.domain.WorkItem;
import org.tobbaumann.wt.domain.WorkItemSummary;

import com.google.common.base.Optional;

public interface WorkTrackingService {

	Optional<Activity> getActivity(String activityName);

	IObservableList getActivities();

	IObservableList getMostUsedActivities(int numberOfActivities);

	IObservableSet readDates();

	void startWorkItem(String activityName);

	//Optional<WorkItem> getActiveWorkItem();

	IObservableList getWorkItems();

	List<WorkItem> getWorkItems(Date date);

	//void createActivity(Activity activity);
	//void createWorkItems(Iterable<WorkItem> workItems);
	//void updateWorkItems(Iterable<WorkItem> workItems);
	//void deleteWorkItems(Iterable<WorkItem> workItems);

	List<WorkItemSummary> getWorkItemSummaries(Date date);

}
