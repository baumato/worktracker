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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newLinkedHashSet;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.domain.DomainFactory;
import org.tobbaumann.wt.domain.DomainPackage;
import org.tobbaumann.wt.domain.WorkItem;
import org.tobbaumann.wt.domain.WorkItemSummary;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;

public class WorkTrackingServiceImpl implements WorkTrackingService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WorkTrackingServiceImpl.class
			.getName());

	private final Set<WorkItem> items = new TreeSet<WorkItem>();

	public WorkTrackingServiceImpl() {
		init();
	}

	private void init() {
		LOGGER.trace("init");
		WorkTrackingServiceInitializer.initialize(this);
	}

	public void activate() {
		LOGGER.trace("activate");
	}

	public void deactivate() {
		LOGGER.trace("deactivate");
	}

	@Override
	public void createWorkItems(Iterable<WorkItem> workItems) {
		List<WorkItem> itemsToAdd = newArrayList();
		for (WorkItem wi : workItems) {
			checkArgument(wi.getID() == null);
			wi.setID(EcoreUtil.generateUUID());
			itemsToAdd.add(wi);
		}
		items.addAll(itemsToAdd);
	}

	@Override
	public void updateWorkItems(Iterable<WorkItem> workItems) {
		List<WorkItem> itemsToUpdate = newArrayList();
		for (WorkItem wi : workItems) {
			checkArgument(wi.getID() != null);
			itemsToUpdate.add(wi);
		}
		items.addAll(itemsToUpdate);
	}

	@Override
	public void deleteWorkItems(Iterable<WorkItem> workItems) {
		items.removeAll(ImmutableList.copyOf(workItems));
	}

	@Override
	public WorkItem readWorkItem(String id) {
		for (WorkItem wi : items) {
			if (wi.getID().equals(id)) {
				return wi;
			}
		}
		throw new NoSuchElementException();
	}

	@Override
	public List<WorkItem> readWorkItems(String strDate) {
		List<WorkItem> itemList = newArrayList();
		for (WorkItem wi : items) {
			String wiDate = DomainFactory.eINSTANCE.convertToString(DomainPackage.Literals.DATE, wi.getStart());
			if (strDate.equals(wiDate)) {
				itemList.add(wi);
			}
		}
		return itemList;
	}

	@Override
	public List<WorkItemSummary> readWorkItemSummaries(String date) {
		List<WorkItem> items = readWorkItems(date);
		Multimap<String, WorkItem> map = ArrayListMultimap.create();
		for (WorkItem wi : items) {
			map.put(wi.getActivityName(), wi);
		}
		List<WorkItemSummary> res = newArrayList();
		for (String a : map.keySet()) {
			Collection<WorkItem> itemsWithActivity = map.get(a);
			WorkItemSummary wis = DomainFactory.eINSTANCE.createWorkItemSummary();
			wis.getWorkItems().addAll(itemsWithActivity);
			res.add(wis);
		}
		return res;
	}

	@Override
	public List<WorkItem> readWorkItems() {
		return ImmutableList.copyOf(items);
	}

	@Override
	public Set<String> readDates() {
		Set<String> dates = newLinkedHashSet();
		DomainFactory df = DomainFactory.eINSTANCE;
		for (WorkItem wi : items) {
			dates.add(df.convertToString(DomainPackage.Literals.DATE, wi.getStart()));
		}
		return dates;
	}
}
