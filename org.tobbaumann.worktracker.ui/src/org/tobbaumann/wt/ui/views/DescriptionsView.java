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
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.tobbaumann.wt.domain.DomainPackage;
import org.tobbaumann.wt.domain.WorkItem;
import org.tobbaumann.wt.domain.WorkItemSummary;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

public class DescriptionsView {

	private Text txtDescr;
	private EObject current;

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
		txtDescr.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				WorkItem wi = (WorkItem) current;
				if (wi != null) {
					wi.setDescription(txtDescr.getText());
				}
			}
		});
	}

	@Inject
	public void updateDescription(@Named(IServiceConstants.ACTIVE_SELECTION) @Optional WorkItem wi) {
		if (wi == null) {
			return;
		}
		this.current = wi;
		txtDescr.setText(Strings.nullToEmpty(wi.getDescription()));
		updateEnablement();
	}

	@Inject
	public void updateDescription(
			@Named(IServiceConstants.ACTIVE_SELECTION) @Optional WorkItemSummary wis) {
		if (wis == null) {
			return;
		}
		this.current = wis;
		String lineSeparator = System.getProperty("line.separator");
		txtDescr.setText(Joiner.on(lineSeparator).join(wis.getSumOfDescriptions()));
		updateEnablement();
	}

	private void updateEnablement() {
		txtDescr.setEnabled(isWorkItem());
		txtDescr.setEditable(isWorkItem());
	}

	private boolean isWorkItem() {
		return current.eClass() == DomainPackage.Literals.WORK_ITEM;
	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void focus() {
		if (txtDescr != null && !txtDescr.isDisposed()) {
			txtDescr.setFocus();
		}
	}
}
