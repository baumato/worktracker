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

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.domain.Activity;
import org.tobbaumann.wt.domain.DomainFactory;
import org.tobbaumann.wt.domain.WorkItem;

import com.google.common.collect.ImmutableSet;

public class WorkTrackingServiceInitializer {

	private final WorkTrackingService service;
	private final Random random = new Random();

	static void initialize(WorkTrackingService service) {
		new WorkTrackingServiceInitializer(service).initialize();
	}

	private WorkTrackingServiceInitializer(WorkTrackingService service) {
		this.service = service;
	}

	private void initialize() {
		service.createWorkItems(createItems());
	}

	private ImmutableSet<WorkItem> createItems() {
		ImmutableSet.Builder<WorkItem> items = ImmutableSet.builder();
		List<String> activities = Arrays.asList(
				"Doing some work", "Doing other work",
				"Even more work", "A meeting", "Task 0815",
				"Coffee break", "Talking to colleagues",
				"Task I don't like", "A bit stretching");
		for (int dayOfMonth = 1; dayOfMonth <= 28; dayOfMonth++) {
			Date startDate = createRandomDateTime(9, dayOfMonth);
			for (int hourOfDay = 10; hourOfDay <= 19; hourOfDay++) {
				Date endDate = createRandomDateTime(hourOfDay, dayOfMonth);
				String activityName = activities.get(random.nextInt(activities.size()));
				items.add(createWorkItem(activityName, startDate, endDate));
				startDate = endDate;
			}
		}
		return items.build();
	}

	private Date createRandomDateTime(int hourOfDay, int dayOfMonth) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(0);
		c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		c.set(Calendar.MONTH, Calendar.FEBRUARY);
		c.set(Calendar.YEAR, 2013);
		c.set(Calendar.HOUR_OF_DAY, hourOfDay);
		c.set(Calendar.MINUTE, random.nextInt(60));
		return c.getTime();
	}

	private WorkItem createWorkItem(String activityName, Date start, Date end) {
		WorkItem wi = DomainFactory.eINSTANCE.createWorkItem();
		Activity a = DomainFactory.eINSTANCE.createActivity();
		a.setName(activityName);
		wi.setActivity(a);
		wi.setStart(start);
		wi.setEnd(end);
		return wi;
	}
}
