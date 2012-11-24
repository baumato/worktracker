package org.baumato.wt.ui.views;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class ActivitiesView {

	public ActivitiesView() {
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		Text txt = new Text(parent, SWT.MULTI);
		txt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		txt.setText("TEST");
	}

	@PreDestroy
	public void dispose() {
	}


}
