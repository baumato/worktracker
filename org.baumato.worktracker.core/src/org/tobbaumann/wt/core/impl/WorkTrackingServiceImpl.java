package org.tobbaumann.wt.core.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newLinkedHashSet;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.domain.Activity;
import org.tobbaumann.wt.domain.DomainFactory;
import org.tobbaumann.wt.domain.DomainPackage;
import org.tobbaumann.wt.domain.WorkItem;

import com.google.common.collect.ImmutableList;

public class WorkTrackingServiceImpl implements WorkTrackingService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WorkTrackingServiceImpl.class.getName());

	private final Set<WorkItem> items = new TreeSet<WorkItem>();

	public void activate() {
		LOGGER.trace("activate");

		Calendar c = Calendar.getInstance();
		Calendar start = (Calendar) c.clone();
		start.set(Calendar.HOUR_OF_DAY, start.get(Calendar.HOUR_OF_DAY) -1);
		Calendar end = c;

		items.add(createWorkItem("Doing some work", start.getTime(), end.getTime()));
		items.add(createWorkItem("Doing other work", end.getTime(), null));

	}

	private WorkItem createWorkItem(String activityName, Date start, Date end) {
		WorkItem wi = DomainFactory.eINSTANCE.createWorkItem();
		Activity a = DomainFactory.eINSTANCE.createActivity();
		a.setName(activityName);
		wi.setActivity(a);
		wi.setID(UUID.randomUUID().toString());
		wi.setStart(start);
		wi.setEnd(end);
		return wi;
	}

	public void deactivate() {
		LOGGER.trace("deactivate");
	}

	@Override
	public void createWorkItems(Iterable<WorkItem> workItems) {
		List<WorkItem> itemsToAdd = newArrayList();
		for (WorkItem wi : workItems) {
			checkArgument(wi.getID() == null);
			wi.setID(UUID.randomUUID().toString());
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
	public List<WorkItem> readWorkItems(Date date) {
		List<WorkItem> itemList = readWorkItems();
		int index = -1;
		for (int i = 0; i < items.size(); i++) {
			WorkItem wi = itemList.get(i);
			Date wiDate = wi.getStart();
			if (wiDate.compareTo(date) == 0 || wiDate.compareTo(date) > 0) {
				index = i;
				break;
			}
		}
		if (index == -1) {
			return ImmutableList.of();
		}
		return itemList.subList(index, itemList.size());
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
