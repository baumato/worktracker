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
import static com.google.common.collect.Maps.newHashMap;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.core.databinding.observable.list.IListChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.ListChangeEvent;
import org.eclipse.core.databinding.observable.list.ListDiffVisitor;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.set.WritableSet;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLParserPool;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLParserPoolImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.domain.Activity;
import org.tobbaumann.wt.domain.DomainFactory;
import org.tobbaumann.wt.domain.DomainPackage;
import org.tobbaumann.wt.domain.WorkItem;
import org.tobbaumann.wt.domain.WorkItemSummary;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Throwables;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
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
		WorkItemsPersister persister = new WorkItemsPersister();
		this.workItems.addListChangeListener(persister);
		this.workItems.addListChangeListener(new WorkItemDatesUpdater());
		//WorkTrackingServiceInitializer.initialize(this);
		persister.load();
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

	@Override
	public IObservableList getActivities() {
		LOGGER.trace("enter getActivities");
		return activities;
	}

	@Override
	public List<Activity> getMostUsedActivities(int numberOfActivities) {
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
		return sorted.subList(0, Math.min(numberOfActivities, sorted.size()));
	}

	@Override
	public IObservableSet readDates() {
		return workItemDates;
	}

	@Override
	public void startWorkItem(String activityName, int numberOfMinutesBeforeNow) {
		Optional<Activity> oa = getActivity(activityName);
		final Activity activity;
		if (oa.isPresent()) {
			activity = oa.get();
		} else {
			activity = createActivity(activityName);
		}

		// update currently active work item
		Calendar nowCal = Calendar.getInstance();
		nowCal.set(Calendar.MINUTE, nowCal.get(Calendar.MINUTE) - numberOfMinutesBeforeNow);
		Date now = nowCal.getTime();

		if (activeWorkItem != null) {
			activeWorkItem.setEndDate(now);
		}

		// add new work item
		WorkItem wi = createWorkItem(activity, now, null, null);
		activeWorkItem = wi;
	}

	@Override
	public IObservableList getWorkItems() {
		LOGGER.trace("enter getWorkItems");
		return workItems;
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

	@Override
	public List<WorkItemSummary> getWorkItemSummaries(Date date) {
		List<WorkItem> items = getWorkItems(date);
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
	public Activity createActivity(String activityName) {
		Optional<Activity> a = getActivity(activityName);
		if (a.isPresent()) {
			throw new ActivityAlreadyExistsException(a.get().getName());
		}
		Activity activity = createActivityInternal(activityName);
		activities.add(activity);
		return activity;
	}

	Activity createActivityInternal(String activityName) {
		Activity activity = DomainFactory.eINSTANCE.createActivity();
		activity.setId(EcoreUtil.generateUUID());
		activity.setName(activityName);
		activity.setOccurrenceFrequency(0);
		return activity;
	}

	void addActivities(final Collection<Activity> as) {
		this.activities.getRealm().exec(new Runnable() {
			@Override
			public void run() {
				activities.addAll(as);
			}
		});
	}

	@Override
	public WorkItem createWorkItem(Activity activity, Date start, Date end, String description) {
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

	void addWorkItems(final Collection<WorkItem> ws) {
		this.workItems.getRealm().exec(new Runnable() {
			@Override
			public void run() {
				workItems.addAll(ws);
			}
		});
	}

	@Override
	public ImportResult importData(String strPath, IProgressMonitor monitor) {
		return new WorkTrackerDataImporter(this).importData(strPath, monitor);
	}

	@Override
	public ImportResult createFakeData(int numberOfDays, IProgressMonitor monitor) {
		return new FakeDataCreator(this).createFakeData(numberOfDays, monitor);
	}


	/**
	 *
	 * @author tobbaumann
	 *
	 */
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

	/**
	 *
	 * @author tobbaumann
	 *
	 */
	private final class WorkItemsPersister extends ResourceFactoryImpl implements IListChangeListener {

		private static final String WORKTRACKER_STORAGE_URI = "storage/worktracker.storage";

		private final List<?> lookupTable = newArrayList();
		private final XMLParserPool parserPool = new XMLParserPoolImpl();
		private Map<?, ?> nameToFeatureMap = newHashMap();


		public WorkItemsPersister() {
			Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		    Map<String, Object> m = reg.getExtensionToFactoryMap();
		    m.put("storage", new XMIResourceFactoryImpl());
		}

		@Override
		public void handleListChange(ListChangeEvent event) {
			commit();
		}

		private void commit() {
			try {
				commitUnchecked();
			} catch (Exception e) {
				Throwables.propagate(e);
			}
		}

		private void commitUnchecked() throws IOException {
			URI uri = URI.createURI(WORKTRACKER_STORAGE_URI);
			XMIResource resource = createResource(uri);
			resource.getContents().addAll(activities);
			resource.getContents().addAll(workItems);
			resource.save(resource.getDefaultSaveOptions());
		}

		public void load() {
			try {
				loadUnchecked();
			} catch (IOException e) {
				Throwables.propagate(e);
			}
		}

		private void loadUnchecked() throws IOException {
			DomainPackage.eINSTANCE.eClass();
		    URI uri = URI.createFileURI(WORKTRACKER_STORAGE_URI);
		    if (!new File(uri.toFileString()).exists()) {
		    	return;
		    }
			XMIResource resource = createResource(uri);
		    List<Activity> activities = newArrayList();
		    List<WorkItem> workItems = newArrayList();
		    resource.load(resource.getDefaultLoadOptions());
		    for (EObject e : resource.getContents()) {
		    	if (isWorkItem(e)) {
		    		workItems.add((WorkItem) e);
		    	} else if (isActivity(e)) {
		    		activities.add((Activity) e);
		    	} else {
		    		throw new RuntimeException("Unknown object: "+ e);
		    	}
		    }
		    addActivities(activities);
		    addWorkItems(workItems);
		}

		@Override
		public XMIResource createResource(URI uri) {
			XMIResource resource = new XMIResourceImpl(uri);
			Map<Object, Object> saveOptions = resource.getDefaultSaveOptions();
			saveOptions.put(XMLResource.OPTION_DECLARE_XML, Boolean.TRUE);
			saveOptions.put(XMLResource.OPTION_CONFIGURATION_CACHE, Boolean.TRUE);
			saveOptions.put(XMLResource.OPTION_USE_CACHED_LOOKUP_TABLE, lookupTable);
			Map<Object, Object> loadOptions = resource.getDefaultSaveOptions();
			loadOptions.put(XMLResource.OPTION_DEFER_ATTACHMENT, Boolean.TRUE);
			loadOptions.put(XMLResource.OPTION_DEFER_IDREF_RESOLUTION, Boolean.TRUE);
			loadOptions.put(XMLResource.OPTION_USE_DEPRECATED_METHODS, Boolean.FALSE);
			loadOptions.put(XMLResource.OPTION_USE_PARSER_POOL, parserPool);
			loadOptions.put(XMLResource.OPTION_USE_XML_NAME_TO_FEATURE_MAP, nameToFeatureMap);
			return resource;
		}

		private boolean isWorkItem(EObject e) {
			return e.eClass().equals(DomainPackage.Literals.WORK_ITEM);
		}

		private boolean isActivity(EObject e) {
			return e.eClass().equals(DomainPackage.Literals.ACTIVITY);
		}
	}
}
