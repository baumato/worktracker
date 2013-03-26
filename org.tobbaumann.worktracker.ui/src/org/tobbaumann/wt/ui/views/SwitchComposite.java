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

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;


public final class SwitchComposite extends Composite {

	private int topControlIndex = 0;
	private final StackLayout layout;

	public SwitchComposite(Composite parent) {
		super(parent, SWT.NONE);
		layout = new StackLayout();
		setLayout(layout);
	}

	public void switchActiveControl() {
		topControlIndex = (topControlIndex +1) % getChildren().length;
		setTopControl(getChildren()[topControlIndex]);
	}

	public Control getTopControl() {
		return layout.topControl;
	}

	public void setTopControl(Control activeControl) {
		for (int i = 0; i < getChildren().length; i++) {
			Control c = getChildren()[i];
			if (c == activeControl) {
				topControlIndex = i;
				break;
			}
		}
		layout.topControl = activeControl;
		layout();
	}
}
