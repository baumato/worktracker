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
package org.tobbaumann.wt.ui.views.wistart;

import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.tobbaumann.wt.core.WorkTrackingService;

/**
 *
 * @author tobbaumann
 *
 */
public class ActivityField extends Composite {

	private Text txtActivity;
	private ContentProposalAdapter activityContentProposalAdapter;
	private WorkTrackingService service;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ActivityField(Composite parent, WorkTrackingService service) {
		super(parent, SWT.NONE);
		this.service = service;
		setLayout(new FillLayout(SWT.HORIZONTAL));
		createContent();
	}

	private void createContent() {
		txtActivity = new Text(this, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		activityContentProposalAdapter = ActivityContentProposalAdapter.applyContentAssist(txtActivity, service);
	}

	public String getText() {
		return txtActivity.getText();
	}

	public void setText(String txt) {
		txtActivity.setText(txt);
	}

	public void addModifyListener(ModifyListener listener) {
		txtActivity.addModifyListener(listener);
	}

	@Override
	public void addKeyListener(KeyListener listener) {
		txtActivity.addKeyListener(listener);
	}

	@Override
	public void addFocusListener(FocusListener listener) {
		txtActivity.addFocusListener(listener);
	}

	public boolean isProposalPopupOpen() {
		return activityContentProposalAdapter != null && activityContentProposalAdapter.isProposalPopupOpen();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
