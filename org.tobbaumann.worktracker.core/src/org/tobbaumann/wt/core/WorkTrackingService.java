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

import static com.google.common.base.Objects.toStringHelper;

import java.util.Date;
import java.util.List;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.tobbaumann.wt.domain.Activity;
import org.tobbaumann.wt.domain.WorkItem;
import org.tobbaumann.wt.domain.WorkItemSummary;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

/**
 *
 * @author tobbaumann
 *
 */
public interface WorkTrackingService {

	IObservableSet readDates();

	Optional<Activity> getActivity(String activityName);
	IObservableList getActivities();
	IObservableList getMostUsedActivities(int numberOfActivities);

	/**
	 * @param activityName the name of the activity
	 * @return the newly create activity
	 * @throws ActivityAlreadyExistsException, see {@link #getActivity(String)}
	 */
	Activity createActivity(String activityName);

	IObservableList getWorkItems();
	List<WorkItem> getWorkItems(Date date);
	WorkItem createWorkItem(Activity activity, Date start, Date end, String description);
	void startWorkItem(String activityName, int numberOfMinutesBeforeNow);

	List<WorkItemSummary> getWorkItemSummaries(Date date);

	/**
	 * Imports the old WorkTracker data.
	 * @param path the path to be searched for old WorkTracker data
	 * @param monitor the progress monitor
	 * @return the description of the done import
	 * @throws OperationCanceledException
	 */
	ImportResult importData(String path, IProgressMonitor monitor);


	/**
	 *
	 * @author tobbaumann
	 *
	 */
	public static final class ImportResult {
		public final int numberOfActivities;
		public final int numberOfWorkItems;
		public final ImmutableList<IStatus> errors;

		public ImportResult(int numberOfActivities, int numberOfWorkItems, Iterable<IStatus> errors) {
			super();
			this.numberOfActivities = numberOfActivities;
			this.numberOfWorkItems = numberOfWorkItems;
			this.errors = ImmutableList.copyOf(errors);
		}

		@Override
		public String toString() {
			return toStringHelper(this)
					.add("numberOfActivities", numberOfActivities)
					.add("numberOfWorkItems", numberOfWorkItems)
					.add("numberOfErrors", errors.size())
					.toString();
		}
	}

	/**
	 *
	 * @author tobbaumann
	 *
	 */
	public static final class ActivityAlreadyExistsException extends RuntimeException {
		public ActivityAlreadyExistsException(String activityName) {
			super(activityName);
		}
	}

	/**
	 *
	 * @author tobbaumann
	 *
	 */
	public static final class OperationCanceledException extends RuntimeException {
	}
}
