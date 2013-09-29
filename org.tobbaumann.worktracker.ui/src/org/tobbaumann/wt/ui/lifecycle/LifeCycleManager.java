package org.tobbaumann.wt.ui.lifecycle;

import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.equinox.app.IApplicationContext;

public class LifeCycleManager {

	@PostContextCreate
	public void postContextCreate(IApplicationContext context) {
		System.out.println("postcontextcreate");
	}
}
