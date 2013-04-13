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
package org.tobbaumann.wt.core.impl;

import static com.google.common.collect.Iterables.getLast;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.eclipse.core.databinding.observable.list.IListChangeListener;
import org.eclipse.core.databinding.observable.list.ListChangeEvent;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLParserPool;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLParserPoolImpl;
import org.tobbaumann.wt.domain.Activity;
import org.tobbaumann.wt.domain.DomainPackage;
import org.tobbaumann.wt.domain.WorkItem;

import com.google.common.base.Throwables;

/**
 *
 * @author tobbaumann
 *
 */
final class WorkItemsPersister extends ResourceFactoryImpl implements IListChangeListener {

	private static final String WORKTRACKER_STORAGE_URI = "storage/worktracker.storage";

	private final WorkTrackingServiceImpl service;
	private final List<?> lookupTable = newArrayList();
	private final XMLParserPool parserPool = new XMLParserPoolImpl();
	private final Map<?, ?> nameToFeatureMap = newHashMap();


	public WorkItemsPersister(WorkTrackingServiceImpl service) {
		this.service = service;
		configure();
	}

	private void configure() {
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
	    Map<String, Object> m = reg.getExtensionToFactoryMap();
	    m.put("storage", new XMIResourceFactoryImpl());
	}

	@Override
	public void handleListChange(ListChangeEvent event) {
		commit();
	}

	private void commit() {
		try {
			commitUnchecked();
		} catch (Exception e) {
			Throwables.propagate(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void commitUnchecked() throws IOException {
		URI uri = URI.createURI(WORKTRACKER_STORAGE_URI);
		XMIResource resource = createResource(uri);
		resource.getContents().addAll(service.getActivities());
		resource.getContents().addAll(service.getWorkItems());
		resource.save(resource.getDefaultSaveOptions());
	}

	public void load() {
		try {
			loadUnchecked();
		} catch (IOException e) {
			Throwables.propagate(e);
		}
	}

	private void loadUnchecked() throws IOException {
		DomainPackage.eINSTANCE.eClass();
	    URI uri = URI.createFileURI(WORKTRACKER_STORAGE_URI);
	    if (!new File(uri.toFileString()).exists()) {
	    	return;
	    }
		XMIResource resource = createResource(uri);
	    List<Activity> activities = newArrayList();
	    List<WorkItem> workItems = newArrayList();
	    resource.load(resource.getDefaultLoadOptions());
	    for (EObject e : resource.getContents()) {
	    	if (isWorkItem(e)) {
	    		workItems.add((WorkItem) e);
	    	} else if (isActivity(e)) {
	    		activities.add((Activity) e);
	    	} else {
	    		throw new RuntimeException("Unknown object: "+ e);
	    	}
	    }
	    service.addActivities(activities);
	    service.addWorkItems(workItems);
	    if (getLast(workItems).getEndDate() == null) {
	    	service.setActiveWorkItem(getLast(workItems));
	    }
	}

	@Override
	public XMIResource createResource(URI uri) {
		XMIResource resource = new XMIResourceImpl(uri);
		Map<Object, Object> saveOptions = resource.getDefaultSaveOptions();
		saveOptions.put(XMLResource.OPTION_DECLARE_XML, Boolean.TRUE);
		saveOptions.put(XMLResource.OPTION_CONFIGURATION_CACHE, Boolean.TRUE);
		saveOptions.put(XMLResource.OPTION_USE_CACHED_LOOKUP_TABLE, lookupTable);
		Map<Object, Object> loadOptions = resource.getDefaultSaveOptions();
		loadOptions.put(XMLResource.OPTION_DEFER_ATTACHMENT, Boolean.TRUE);
		loadOptions.put(XMLResource.OPTION_DEFER_IDREF_RESOLUTION, Boolean.TRUE);
		loadOptions.put(XMLResource.OPTION_USE_DEPRECATED_METHODS, Boolean.FALSE);
		loadOptions.put(XMLResource.OPTION_USE_PARSER_POOL, parserPool);
		loadOptions.put(XMLResource.OPTION_USE_XML_NAME_TO_FEATURE_MAP, nameToFeatureMap);
		return resource;
	}

	private boolean isWorkItem(EObject e) {
		return e.eClass().equals(DomainPackage.Literals.WORK_ITEM);
	}

	private boolean isActivity(EObject e) {
		return e.eClass().equals(DomainPackage.Literals.ACTIVITY);
	}
}