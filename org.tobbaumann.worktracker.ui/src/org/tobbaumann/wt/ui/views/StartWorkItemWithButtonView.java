package org.tobbaumann.wt.ui.views;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.domain.Activity;

public class StartWorkItemWithButtonView {

	private final WorkTrackingService service;
	private Composite parent;

	@Inject
	public StartWorkItemWithButtonView(WorkTrackingService service) {
		this.service = service;
	}

	@PostConstruct
	public void createControls(Composite parent) {
		this.parent = parent;
		createMostUsedActivitiesButtons();
	}

	private void createMostUsedActivitiesButtons() {
		GridLayout layout = new GridLayout(1, true);
		parent.setLayout(layout);
		IObservableList mua = service.getMostUsedActivities(6);
		for (Object o : mua) {
			Activity a = (Activity) o;
			Button btn = new Button(parent, SWT.PUSH);
			btn.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			btn.setText(a.getName());
		}
	}

	@Focus
	public void onFocus() {
		if (parent != null && !parent.isDisposed()) {
			parent.setFocus();
		}
	}

}