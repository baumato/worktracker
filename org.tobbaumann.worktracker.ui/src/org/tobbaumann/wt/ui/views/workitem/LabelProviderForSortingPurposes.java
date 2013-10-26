/*******************************************************************************
 * Copyright (c) 2013 tobba_000.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     tobba_000 - initial API and implementation
 ******************************************************************************/
package org.tobbaumann.wt.ui.views.workitem;

import org.eclipse.jface.viewers.LabelProvider;
import org.tobbaumann.wt.domain.WorkItem;

final class LabelProviderForSortingPurposes extends LabelProvider {

	@Override
	public String getText(Object element) {
		WorkItem item = (WorkItem) element;
		return String.valueOf(item.getStart().getTime());
	}
}
