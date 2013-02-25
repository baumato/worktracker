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
package org.tobbaumann.wt.ui.views;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.tobbaumann.wt.domain.WorkItem;
import org.tobbaumann.wt.domain.WorkItemSummary;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

public class DescriptionsView {

	private Text txtDescr;

	@Inject
	private ESelectionService selectionService;

	public DescriptionsView() {
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		txtDescr = new Text(parent, SWT.MULTI);
		txtDescr.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		txtDescr.setText("");
	}

	@Inject
	public void updateDescription(@Named(IServiceConstants.ACTIVE_SELECTION) @Optional WorkItem wi) {
		if (wi == null) {
			return;
		}
		txtDescr.setText(Strings.nullToEmpty(wi.getDescription()));
	}

	@Inject
	public void updateDescription(@Named(IServiceConstants.ACTIVE_SELECTION) @Optional WorkItemSummary wis) {
		if (wis == null) {
			return;
		}
		String lineSeparator = System.getProperty("line.separator");
		txtDescr.setText(Joiner.on(lineSeparator).join(wis.getSumOfDescriptions()));
	}

	@PreDestroy
	public void dispose() {
	}


}
