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
package org.tobbaumann.wt.ui.views.date;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;

public class HtmlToolTip extends ToolTip {

	private String toolTipText;
	private Point size;

	static void apply(Control control, String toolTipText, int width, int height) {
		new HtmlToolTip(control, toolTipText, width, height);
	}

	private HtmlToolTip(Control control, String toolTipText, int width, int height) {
		super(control);
		this.toolTipText = toolTipText;
		this.size = new Point(width, height);
	}

	@Override
	protected Composite createToolTipContentArea(Event event, Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().spacing(0, 0).applyTo(comp);
		Browser browser = new Browser(comp, SWT.NO_SCROLL);
		browser.setText(toolTipText);
		browser.setLayoutData(new GridData(size.x, size.y));
		return comp;
	}

}
