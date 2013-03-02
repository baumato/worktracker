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
package org.tobbaumann.wt.core;

import static com.google.common.base.Preconditions.checkArgument;

import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkTrackerStorage {

	private static final Logger LOGGER = LoggerFactory.getLogger(WorkTrackerStorage.class);
	private static final String EXTENSION = "storage";
	private static final String STORAGE_FILE_NAME = "WorkTracker" + EXTENSION;
	private static final XMIResourceFactoryImpl FACTORY = new XMIResourceFactoryImpl();

	private final PropertyChangeSupport pcs;
	private final File root;
	private ResourceSet resourceSet;
	private boolean dirty;

	public WorkTrackerStorage(String root) {
		this.root = new File(root);
		checkArgument(this.root.isDirectory());
		checkArgument(this.root.canWrite());
		this.pcs = new PropertyChangeSupport(this);
	}

	public void commit() {
		try {
			LOGGER.debug("Storing resources to {}.", root.getAbsolutePath());
			for (Resource resource : getResourceSet().getResources()) {
				resource.save(null);
			}
			setDirty(false);
		} catch (Exception ex) {
			throw new FileSystemStorageException(ex);
		}
	}



	private synchronized ResourceSet getResourceSet() {
		if (resourceSet == null) {
			resourceSet = createResourceSet();
		}
		return resourceSet;
	}

	public void rollback() {
		reload();
	}

	private void reload() {
		ResourceSet resourceSet = createResourceSet();
		firePropertyChange("resourceSet", this.resourceSet, this.resourceSet = resourceSet);
		setDirty(false);
	}

	private ResourceSet createResourceSet() {
		ResourceSet resourceSet = new ResourceSetImpl() {
			@Override
			protected void demandLoad(Resource resource) throws IOException {
				super.demandLoad(resource);
				// Listen to model changes and adjust the dirty state
				// accordingly
				resource.eAdapters().add(new EContentAdapter() {
					@Override
					public void notifyChanged(Notification notification) {
						super.notifyChanged(notification);
						if (!notification.isTouch()) {
							setDirty(true);
						}
					}
				});
			}
		};
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(EXTENSION, FACTORY);
		return resourceSet;
	}

	private void setDirty(boolean dirty) {
		firePropertyChange("dirty", this.dirty, this.dirty = dirty);
	}

	private void firePropertyChange(String propertyValue, Object oldValue, Object newValue) {
		pcs.firePropertyChange(propertyValue, oldValue, newValue);
	}

	private URI getURI() {
		String absolutePath = new File(root, STORAGE_FILE_NAME).getAbsolutePath();
		return URI.createFileURI(absolutePath);
	}

	public boolean isDirty() {
		return dirty;
	}

	public void dispose() {
		resourceSet = null;
	}

	/**
	 * 
	 * @author tobbaumann
	 *
	 */
	public static final class FileSystemStorageException extends RuntimeException {
		public FileSystemStorageException(Exception e) {
			super(e);
		}
	}


}
