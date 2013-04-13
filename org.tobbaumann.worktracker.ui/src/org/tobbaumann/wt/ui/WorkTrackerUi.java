package org.tobbaumann.wt.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class WorkTrackerUi implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		setupSystemTrayOnDisplayActivation();
	}

	private void setupSystemTrayOnDisplayActivation() {
		Display.getCurrent().addFilter(SWT.Activate, new Listener() {
			@Override
			public void handleEvent(Event event) {
				SystemTray.setupTray((Shell) event.widget);
				Display.getCurrent().removeFilter(SWT.Activate, this);
			}
		});
	}

	@Override
	public void stop(BundleContext context) throws Exception {
	}
}
