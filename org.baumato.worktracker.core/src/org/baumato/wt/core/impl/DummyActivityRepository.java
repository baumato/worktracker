/*******************************************************************************
 * Copyright (c) 2012 tobbaumann.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     tobbaumann - initial API and implementation
 ******************************************************************************/
package org.baumato.wt.core.impl;

import static com.google.common.base.Strings.padStart;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.baumato.wt.core.Activity;
import org.baumato.wt.core.ActivityDate;
import org.baumato.wt.core.ActivityName;
import org.baumato.wt.core.ActivityRepository;
import org.baumato.wt.core.CoreFactory;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;

public class DummyActivityRepository implements ActivityRepository {

	public DummyActivityRepository() {
	}
	
	
	@Override
	public Activity getActivities() {
		Activity a = CoreFactory.eINSTANCE.createActivity();
		ActivityName an = CoreFactory.eINSTANCE.createActivityName();
		an.setName("First activity");
		a.setActivityName(an);
		ActivityDate d = CoreFactory.eINSTANCE.createActivityDate();
		d.setDate("2012-11-24 13:19:00");
		a.setStart(d);
		return a;
	}

	@Override
	public IObservableList getActivityDates() {
		List<String> dates = newArrayList();
		for (int i = 1; i <= 30; i++) {
			dates.add("2012-11-" + padStart("" + i, 2, '0'));
		}
		return new WritableList(dates, null);
	}

}
