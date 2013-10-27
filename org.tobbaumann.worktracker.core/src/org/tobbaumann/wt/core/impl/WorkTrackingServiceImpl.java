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
package org.tobbaumann.wt.core.impl;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.eclipse.core.databinding.observable.list.IListChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.ListChangeEvent;
import org.eclipse.core.databinding.observable.list.ListDiffVisitor;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.set.WritableSet;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.domain.Activity;
import org.tobbaumann.wt.domain.DomainFactory;
import org.tobbaumann.wt.domain.WorkItem;
import org.tobbaumann.wt.domain.WorkItemSummary;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.common.collect.Ordering;

public class WorkTrackingServiceImpl implements WorkTrackingService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WorkTrackingServiceImpl.class
			.getName());

	private final IObservableList<Activity> activities;
	private final IObservableSet<Date> workItemDates;
	private final IObservableList<WorkItem> workItems;
	private final WorkItemsPersister persister;
	private WorkItem activeWorkItem;

	public WorkTrackingServiceImpl() {
		LOGGER.trace("init");
		this.activities = new WritableList<>();
		this.workItemDates = new WritableSet<>();
		this.workItems = new WritableList<>();
		this.workItems.addListChangeListener(new WorkItemDatesUpdater());
		this.persister = new WorkItemsPersister(this);
	}

	@Override
	public void startWorkItem(String activityName, int numberOfMinutesBeforeNow) {
		Activity activity = getActivityFromString(activityName);

		// update currently active work item
		Calendar nowCal = Calendar.getInstance();
		nowCal.set(Calendar.MINUTE, nowCal.get(Calendar.MINUTE) - numberOfMinutesBeforeNow);
		Date now = nowCal.getTime();

		if (activeWorkItem != null) {
			activeWorkItem.setEndDate(now);
		}

		// add new work item
		WorkItem wi = createAndAddWorkItem(activity, now, null, null);
		setActiveWorkItem(wi);
	}

	@Override
	public void addWorkItem(String activityName, Date start, Date end, String description) {
		Activity activity = getActivityFromString(activityName);
		createAndAddWorkItem(activity, start, end, description);
	}

	@Override
	public void removeWorkItem(WorkItem wi) {
		final int indexOfWi = getWorkItems().indexOf(checkNotNull(wi));
		if (indexOfWi < 0) {
			throw new WorkItemDoesNotExistException();
		}
		Activity a = wi.getActivity();
		getWorkItems().remove(indexOfWi);
		if (getActiveWorkItem().isPresent() && getActiveWorkItem().get().getId().equals(wi.getId())) {
			setActiveWorkItem(null);
		}
		removeActivityIfNotUsed(a);
	}

	private void removeActivityIfNotUsed(Activity a) {
		for (WorkItem wi : getWorkItems()) {
			if (wi.getActivity().getId().equals(a.getId())) {
				return;
			}
		}
		getActivities().remove(a);
	}

	private Activity getActivityFromString(String activityName) {
		Optional<Activity> oa = getActivity(activityName);
		final Activity activity;
		if (oa.isPresent()) {
			activity = oa.get();
		} else {
			activity = createActivity(activityName);
		}
		activity.setInUse(true);
		return activity;
	}

	private Activity createActivity(String activityName) {
		Optional<Activity> a = getActivity(activityName);
		if (a.isPresent()) {
			throw new ActivityAlreadyExistsException(a.get().getName());
		}
		Activity activity = createActivityInternal(activityName);
		activities.add(activity);
		return activity;
	}

	@Override
	public Optional<Activity> getActivity(final String activityName) {
		LOGGER.trace("enter getActivity - {}", activityName);
		return getActivity(this.activities, activityName);
	}

	Optional<Activity> getActivity(List<Activity> activities, final String activityName) {
		LOGGER.trace("enter getActivity - {}", activityName);
		Activity a = Iterables.find(activities, new Predicate<Activity>() {
			@Override
			public boolean apply(Activity a) {
				if (a.getName().equalsIgnoreCase(activityName)) {
					return true;
				}
				return false;
			}
		}, null);
		return Optional.fromNullable(a);
	}

	Activity createActivityInternal(String activityName) {
		Activity activity = DomainFactory.eINSTANCE.createActivity();
		activity.setId(EcoreUtil.generateUUID());
		activity.setName(activityName);
		activity.setInUse(true);
		activity.setOccurrenceFrequency(0);
		return activity;
	}

	private WorkItem createAndAddWorkItem(Activity activity, Date start, Date end, String description) {
		WorkItem wi = createWorkItemInternal(activity, start, end, description);
		workItems.add(wi);
		return wi;
	}

	WorkItem createWorkItemInternal(Activity activity, Date start, Date end, String description) {
		WorkItem wi = DomainFactory.eINSTANCE.createWorkItem();
		wi.setId(EcoreUtil.generateUUID());
		wi.setActivity(checkNotNull(activity));
		activity.incrementOccurrenceFrequency();
		wi.setStart(checkNotNull(start));
		if (end != null) {
			wi.setEndDate(end);
		}
		if (description != null) {
			wi.setDescription(description);
		}
		return wi;
	}

	void setActiveWorkItem(WorkItem activeWorkItem) {
		this.activeWorkItem = activeWorkItem;
	}

	@Override
	public List<WorkItemSummary> getWorkItemSummaries(Date date) {
		List<WorkItem> items = getWorkItems(date);
		return createSummaries(items);
	}

	@Override
	public List<WorkItemSummary> getWorkItemSummaries(int weekInYear) {
		List<WorkItem> items = newArrayList();
		Calendar c = Calendar.getInstance();
		c.set(Calendar.WEEK_OF_YEAR, weekInYear);
		List<Integer> days = Arrays.asList(
			Calendar.MONDAY,
			Calendar.TUESDAY,
			Calendar.WEDNESDAY,
			Calendar.THURSDAY,
			Calendar.FRIDAY,
			Calendar.SATURDAY,
			Calendar.SUNDAY);
		for (int day : days) {
			c.set(Calendar.DAY_OF_WEEK, day);
			items.addAll(getWorkItems(c.getTime()));
		}
		return createSummaries(items);
	}

	@Override
	public List<WorkItem> getWorkItems(Date date) {
		List<WorkItem> itemList = newArrayList();
		for (Object o : workItems) {
			WorkItem wi = (WorkItem) o;
			String wiDate = toString(wi.getStart());
			if (wiDate.equals(toString(date))) {
				itemList.add(wi);
			}
		}
		return itemList;
	}

	private String toString(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	private List<WorkItemSummary> createSummaries(List<WorkItem> items) {
		Multimap<String, WorkItem> map = ArrayListMultimap.create();
		for (Object o : items) {
			WorkItem wi = (WorkItem) o;
			map.put(wi.getActivityName(), wi);
		}
		ImmutableList.Builder<WorkItemSummary> res = ImmutableList.builder();
		for (String a : map.keySet()) {
			Collection<WorkItem> itemsWithActivity = map.get(a);
			WorkItemSummary wis = DomainFactory.eINSTANCE.createWorkItemSummary();
			wis.getWorkItems().addAll(itemsWithActivity);
			res.add(wis);
		}
		return res.build();
	}

	@Override
	public void endActiveWorkItem() {
		if (getActiveWorkItem().isPresent()) {
			getActiveWorkItem().get().setEndDate(new Date());
			persister.commit();
		}
	}

	@Override
	public Optional<WorkItem> getActiveWorkItem() {
		return Optional.fromNullable(activeWorkItem);
	}

	public void activate() {
		LOGGER.trace("activate - perform load");
		persister.load();
		// add persister as list change listener after load to save one commit
		this.workItems.addListChangeListener(persister);
	}

	public void deactivate() {
		LOGGER.trace("deactivate");
	}

	@Override
	public IObservableList<Activity> getActivities() {
		LOGGER.trace("enter getActivities");
		return activities;
	}

	@Override
	public List<Activity> getMostUsedActivities(int numberOfActivities) {
		LOGGER.trace("enter getMostUsedActivities - {}", numberOfActivities);
		List<Activity> sorted = newArrayList(activities);
		Comparator<Activity> order = new Comparator<Activity>() {
			@Override
			public int compare(Activity o1, Activity o2) {
				return ComparisonChain.start()
						.compare(o1.getOccurrenceFrequency(), o2.getOccurrenceFrequency())
						.result();
			}
		};
		Collections.sort(sorted, Ordering.from(order).reverse());
		return sorted.subList(0, Math.min(numberOfActivities, sorted.size()));
	}

	@Override
	public IObservableSet<Date> readDates() {
		return workItemDates;
	}

	@Override
	public IObservableList<WorkItem> getWorkItems() {
		LOGGER.trace("enter getWorkItems");
		return workItems;
	}

	@Override
	public CreationResult importData(String strPath, IProgressMonitor monitor) {
		return new WorkTrackerDataImporter(this).importData(strPath, monitor);
	}

	@Override
	public CreationResult createFakeData(int numberOfDays, IProgressMonitor monitor) {
		return new FakeDataCreator(this).createFakeData(numberOfDays, monitor);
	}

	void addActivities(final Collection<Activity> as) {
		this.activities.getRealm().exec(new Runnable() {
			@Override
			public void run() {
				activities.addAll(as);
			}
		});
	}

	void addWorkItems(final Collection<WorkItem> ws) {
		this.workItems.getRealm().exec(new Runnable() {
			@Override
			public void run() {
				workItems.addAll(ws);
			}
		});
	}


	/**
	 *
	 * @author tobbaumann
	 *
	 */
	private final class WorkItemDatesUpdater implements IListChangeListener<EObject> {
		@Override
		public void handleListChange(ListChangeEvent<EObject> event) {
			event.diff.accept(new ListDiffVisitor<EObject>() {
				@Override
				public void handleRemove(int index, EObject element) {
					WorkItem wi = (WorkItem) element;
					Date date = wi.getDatePartOfStart();
					if (getWorkItems(date).isEmpty()) {
						workItemDates.remove(date);
					}
				}

				@Override
				public void handleAdd(int index, EObject element) {
					WorkItem wi = (WorkItem) element;
					workItemDates.add(wi.getDatePartOfStart());
				}
			});
		}
	}
}
