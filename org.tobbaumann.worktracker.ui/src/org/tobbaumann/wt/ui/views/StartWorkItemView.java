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

import java.net.URI;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.domain.Activity;

import com.google.common.collect.Ordering;

public class StartWorkItemView {

	@Inject
	private WorkTrackingService service;

	private Composite leftCompParent;
	private Text txtActivity;
	private TableViewer activitiesTable;



	public StartWorkItemView() {
	}

	@PreDestroy
	public void dispose() {
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	private void createControls(Composite parent) {
		SashForm sashForm = new SashForm(parent, SWT.HORIZONTAL);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		leftCompParent = new Composite(sashForm, SWT.NONE);
		createMostUsedActivitiesButtons(leftCompParent);

		Composite rightCompParent = new Composite(sashForm, SWT.NONE);
		rightCompParent.setLayout(new FillLayout(SWT.HORIZONTAL));
		createActivitiesTable(rightCompParent);
		updateActivitiesTable();

		sashForm.setWeights(new int[] { 1, 1 });
	}

	private void createMostUsedActivitiesButtons(Composite leftCompParent) {
		GridLayout layout = new GridLayout(1, true);
		leftCompParent.setLayout(layout);
		IObservableList mua = service.getMostUsedActivities(6);
		for (Object o : mua) {
			Activity a = (Activity) o;
			Button btn = new Button(leftCompParent, SWT.PUSH);
			btn.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			btn.setText(a.getName());
		}
	}

	private void createActivitiesTable(Composite rightCompParent) {
		Composite parent = new Composite(rightCompParent, SWT.NONE);
		parent.setLayout(new GridLayout(1, false));

		createActivitiesTableStripe(parent);

		activitiesTable = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		activitiesTable.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		activitiesTable.setLabelProvider(new ChangeActivitiesViewLabelProvider());
		activitiesTable.setContentProvider(new ObservableListContentProvider());
		activitiesTable.setComparator(new ViewerComparator(Ordering.natural()));
		activitiesTable.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				Activity selectedActivity = (Activity) ((IStructuredSelection)event.getSelection()).getFirstElement();
				txtActivity.setText(selectedActivity.getName());
			}
		});
	}

	private void createActivitiesTableStripe(Composite parent) {
		Composite stripe = new Composite(parent, SWT.NONE);
		stripe.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		stripe.setLayout(new GridLayout(2, false));
		createActivityTextField(stripe);
		createStartWorkItemButton(stripe);
	}

	private void createActivityTextField(Composite stripe) {
		txtActivity = new Text(stripe, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		txtActivity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		txtActivity.setMessage("Enter activity here...");
		startWorkItemsOnKeyboardShortcut();
	}

	private void startWorkItemsOnKeyboardShortcut() {
		txtActivity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (enterPressed(e) || altAPressed(e)) {
					startWorkItem();
				}
			}

		});
	}

	private boolean enterPressed(KeyEvent e) {
		return e.character == SWT.CR;
	}

	private boolean altAPressed(KeyEvent e) {
		return ((e.stateMask & SWT.ALT) != 0)
				&& (e.character == 'a');
	}

	private void startWorkItem() {
		service.startWorkItem(txtActivity.getText());
		txtActivity.setText("");
	}

	private void createStartWorkItemButton(Composite stripe) {
		Button btnAdd = new Button(stripe, SWT.PUSH);
		btnAdd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		btnAdd.setImage(getAddImage());
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				startWorkItem();
			}
		});
	}

	private Image getAddImage() {
		try {
			ImageRegistry imageRegistry = JFaceResources.getImageRegistry();
			Image imgAdd = imageRegistry.get("Add_Icon");
			if (imgAdd == null) {
				URL imgUrl = URI.create("platform:/plugin/org.tobbaumann.worktracker.ui/icons/add.png").toURL();
				ImageDescriptor imageDescriptor = ImageDescriptor.createFromURL(imgUrl);
				imageRegistry.put("Add_Icon", imageDescriptor);
				return imageRegistry.get("Add_Icon");
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	private void updateActivitiesTable() {
		IObservableList activities = service.getActivities();
		activitiesTable.setInput(activities);
	}

	@Focus
	public void requestFocus() {
		if (txtActivity != null && !txtActivity.isDisposed()) {
			txtActivity.setFocus();
		}
	}

	/**
	 *
	 * @author tobbaumann
	 *
	 */
	private class ChangeActivitiesViewLabelProvider extends StyledCellLabelProvider implements ILabelProvider {

		Styler styler = new FrequenzStyler();

		@Override
		public void update(ViewerCell cell) {
			Activity a = (Activity) cell.getElement();
			StyledString text = new StyledString();
			text.append(a.getName(), StyledString.DECORATIONS_STYLER);
			text.append(" (" + a.getOccurrenceFrequency() + ") ", styler);
			cell.setText(text.toString());
			cell.setStyleRanges(text.getStyleRanges());
			super.update(cell);
		}

		@Override
		public Image getImage(Object element) {
			return null;
		}

		@Override
		public String getText(Object element) {
			Activity a = (Activity) element;
			return a.getName() + " (" + a.getOccurrenceFrequency() + ")";
		}

		private final class FrequenzStyler extends Styler {
			private static final String FREQUENZ_STYLER_FONT = "FrequenzStylerFont";

			FrequenzStyler() {
				JFaceResources.getColorRegistry().put(JFacePreferences.COUNTER_COLOR,
						new RGB(0, 127, 174));
				FontData[] fd = activitiesTable.getTable().getFont().getFontData();
				fd[0].setHeight(fd[0].getHeight() - 2);
				JFaceResources.getFontRegistry().put(FREQUENZ_STYLER_FONT, fd);
			}

			@Override
			public void applyStyles(TextStyle textStyle) {
				textStyle.font = JFaceResources.getFontRegistry().get(FREQUENZ_STYLER_FONT);
				Color color = JFaceResources.getColorRegistry().get(JFacePreferences.COUNTER_COLOR);
				textStyle.foreground = color;
			}
		}
	}
}
