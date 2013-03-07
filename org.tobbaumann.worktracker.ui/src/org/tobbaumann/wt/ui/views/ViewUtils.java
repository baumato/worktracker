package org.tobbaumann.wt.ui.views;

import java.util.concurrent.TimeUnit;

import org.eclipse.jface.viewers.StructuredViewer;

public class ViewUtils {

	private ViewUtils() {
	}

	static void refreshViewerPeriodically(final StructuredViewer viewer, final long delay, final TimeUnit timeUnit) {
		Long millis = TimeUnit.MILLISECONDS.convert(delay, timeUnit);
		viewer.getControl().getDisplay().timerExec(millis.intValue(), new Runnable() {
			@Override
			public void run() {
				if (viewer != null && !viewer.getControl().isDisposed()) {
					viewer.refresh(true);
					refreshViewerPeriodically(viewer, delay, timeUnit);
				}
			}
		});
	}

}
