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
package org.baumato.wt.ui.views;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.baumato.wt.core.ActivityRepository;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

public class DatesView {

	private final ActivityRepository repo;
	
	@Inject
	public DatesView(ActivityRepository repository) {
		this.repo = repository;
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		TableViewer viewer = new TableViewer(parent, SWT.FULL_SELECTION);
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new LabelProvider());
		viewer.setInput(repo.getActivityDates());
		viewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	@PreDestroy
	public void dispose() {
	}

}
