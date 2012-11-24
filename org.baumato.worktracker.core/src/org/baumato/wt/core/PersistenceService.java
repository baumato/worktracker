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
package org.baumato.wt.core;

import java.util.List;

public interface PersistenceService {

	List<String> getActivityDates();
	Activity getActivities();
	
}
