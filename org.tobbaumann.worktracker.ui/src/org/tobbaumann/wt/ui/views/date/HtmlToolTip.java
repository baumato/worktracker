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

	public HtmlToolTip(Control control, String toolTipText, int width, int height) {
		super(control);
		this.toolTipText = toolTipText;
		this.size = new Point(width, height);
	}

	@Override
	protected Composite createToolTipContentArea(Event event, Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().spacing(0, 0).applyTo(comp);
		Browser browser = new Browser(comp, SWT.BORDER | SWT.NO_SCROLL);
		browser.setText(toolTipText);
		browser.setLayoutData(new GridData(size.x, size.y));
		return comp;
	}

}
