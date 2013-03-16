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

import java.util.concurrent.TimeUnit;

import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;

public class ViewUtils {

	private ViewUtils() {
	}

	static void refreshViewerPeriodically(final StructuredViewer viewer) {
		refreshViewerPeriodically(viewer, 1, TimeUnit.SECONDS);
	}

	private static void refreshViewerPeriodically(final StructuredViewer viewer, final long delay, final TimeUnit timeUnit) {
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

	static void requestFocusOnMouseEnter(final StructuredViewer viewer) {
		viewer.getControl().addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				viewer.getControl().setFocus();
			}
		});
	}

}
