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

import static com.google.common.collect.Lists.newArrayList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.domain.Activity;
import org.tobbaumann.wt.domain.DomainFactory;
import org.tobbaumann.wt.domain.WorkItem;
import org.tobbaumann.wt.domain.WorkItemSummary;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Throwables;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.common.collect.Ordering;

public class WorkTrackingServiceImpl implements WorkTrackingService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WorkTrackingServiceImpl.class
			.getName());

	private final IObservableList activities;
	private final IObservableSet workItemDates;
	private final IObservableList workItems;
	private WorkItem activeWorkItem;

	public WorkTrackingServiceImpl() {
		LOGGER.trace("init");
		this.activities = new WritableList(newArrayList(), Activity.class);
		this.workItemDates = new WritableSet(newArrayList(), Date.class);
		this.workItems = new WritableList(newArrayList(), WorkItem.class);
		this.workItems.addListChangeListener(new WorkItemDatesUpdater());
		WorkTrackingServiceInitializer.initialize(this);
	}

	public void activate() {
		LOGGER.trace("activate");
	}

	public void deactivate() {
		LOGGER.trace("deactivate");
	}

	@Override
	@SuppressWarnings("unchecked")
	public Optional<Activity> getActivity(final String activityName) {
		LOGGER.trace("enter getActivity - {}", activityName);
		return Iterables.tryFind(activities, new Predicate<Activity>() {
			@Override
			public boolean apply(Activity a) {
				if (a.getName().equals(activityName)) {
					return true;
				}
				return false;
			}
		});
	}

	@Override
	public IObservableList getActivities() {
		LOGGER.trace("enter getActivities");
		return activities;
	}

	@Override
	public IObservableList getMostUsedActivities(int numberOfActivities) {
		LOGGER.trace("enter getMostUsedActivities - {}", numberOfActivities);
		@SuppressWarnings("unchecked")
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
		WritableList res = new WritableList(sorted.subList(0, numberOfActivities), Activity.class);
		res.addListChangeListener(new ActivityListChangeListener());
		return res;
	}

	@Override
	public IObservableSet readDates() {
		return workItemDates;
	}

	@Override
	public void startWorkItem(String activityName) {
		Optional<Activity> oa = getActivity(activityName);
		final Activity activity;
		if (oa.isPresent()) {
			activity = oa.get();
			activity.incrementOccurrenceFrequency();
		} else {
			activity = DomainFactory.eINSTANCE.createActivity();
			activity.setName(activityName);
			activities.add(activity);
		}

		// update currently active work item
		Date now = new Date();
		if (activeWorkItem != null) {
			activeWorkItem.setEndDate(now);
		}

		// add new work item
		WorkItem wi = DomainFactory.eINSTANCE.createWorkItem();
		wi.setId(EcoreUtil.generateUUID());
		wi.setActivity(activity);
		wi.setStart(now);
		activeWorkItem = wi;

		workItems.add(wi);
	}

	@Override
	public IObservableList getWorkItems() {
		LOGGER.trace("enter getWorkItems");
		return workItems;
	}

	@Override
	public IObservableList getWorkItems(Date date) {
		List<WorkItem> itemList = newArrayList();
		for (Object o : workItems) {
			WorkItem wi = (WorkItem) o;
			String wiDate = toString(wi.getStart());
			if (wiDate.equals(toString(date))) {
				itemList.add(wi);
			}
		}
		WritableList res = new WritableList(itemList, WorkItem.class);
		res.addListChangeListener(new WorkItemListChangeListener());
		return res;
	}

	private String toString(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	@Override
	public List<WorkItemSummary> getWorkItemSummaries(Date date) {
		IObservableList items = getWorkItems(date);
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

	private void store() {
		try {
			ResourceSet resourceSet = new ResourceSetImpl();
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
			.put("storage", new XMIResourceFactoryImpl());

			Resource res = resourceSet.createResource(URI
					.createFileURI("/tmp/storage/worktracker.storage"));
			res.getContents().addAll(activities);
			res.getContents().addAll(workItems);

			for (Resource resource : resourceSet.getResources()) {
				resource.save(null);
			}
		} catch (Exception e) {
			Throwables.propagate(e);
		}
	}

	private final class WorkItemDatesUpdater implements IListChangeListener {
		@Override
		public void handleListChange(ListChangeEvent event) {
			event.diff.accept(new ListDiffVisitor() {
				@Override
				public void handleRemove(int index, Object element) {
					WorkItem wi = (WorkItem) element;
					Date date = getDatePartOfWorkItemStart(wi);
					if (getWorkItems(date).isEmpty()) {
						workItemDates.remove(date);
					}
				}

				@Override
				public void handleAdd(int index, Object element) {
					WorkItem wi = (WorkItem) element;
					workItemDates.add(getDatePartOfWorkItemStart(wi));
				}
			});
		}

		private Date getDatePartOfWorkItemStart(WorkItem wi) {
			try {
				DateFormat df = DateFormat.getDateInstance();
				return df.parse(wi.formatStart(df));
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
	}


	private final class ActivityListChangeListener implements IListChangeListener {
		@Override
		public void handleListChange(ListChangeEvent event) {
			event.diff.accept(new ListDiffVisitor() {
				@Override
				public void handleRemove(int index, Object element) {
					Activity a = (Activity) element;
					activities.remove(a.getName());
				}

				@Override
				public void handleAdd(int index, Object element) {
					Activity a = (Activity) element;
					activities.add(a);
				}
			});
		}
	}


	private final class WorkItemListChangeListener implements IListChangeListener {
		@Override
		public void handleListChange(ListChangeEvent event) {
			event.diff.accept(new ListDiffVisitor() {
				@Override
				public void handleRemove(int index, Object element) {
					WorkItem wi = (WorkItem) element;
					workItems.remove(wi.getId());
				}

				@Override
				public void handleAdd(int index, Object element) {
					WorkItem wi = (WorkItem) element;
					workItems.remove(wi);
				}
			});
		}
	}
}
