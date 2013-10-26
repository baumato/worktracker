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
package org.tobbaumann.wt.ui.views.workitem;

import java.util.Calendar;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;

/**
 *
 * @author tobbaumann
 *
 */
public class TimeCellEditor extends CellEditor {

	private DateTime dateTime;

	public TimeCellEditor(Composite parent) {
		super(parent, SWT.NONE);
	}

	@Override
	protected Control createControl(Composite parent) {
		dateTime = createDateTime(parent);
		applyListeners();
		return dateTime;
	}

	private DateTime createDateTime(Composite parent) {
		return new DateTime(parent, applyStyle());
	}

	private int applyStyle() {
		return getStyle() | SWT.TIME;
	}

	private void applyListeners() {
		applyKeyPressedListener();
		applySelectionListener();
		applyTraversalListener();
		applyFocusListener();
	}

	private void applyKeyPressedListener() {
		dateTime.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				keyReleaseOccured(e);
			}
		});
	}

	@Override
	protected void keyReleaseOccured(KeyEvent keyEvent) {
		if (keyEvent.character == '\u001b') { // Escape character
			fireCancelEditor();
		} else if (keyEvent.character == '\t') { // tab key
			applyEditorValueAndDeactivate();
		}
	}

	private void applyEditorValueAndDeactivate() {
		Object newValue = doGetValue();
		markDirty();
		boolean isValid = isCorrect(newValue);
		setValueValid(isValid);
		fireApplyEditorValue();
		deactivate();
	}

	@Override
	protected Object doGetValue() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0);
		cal.set(
			dateTime.getYear(),
			dateTime.getMonth(),
			dateTime.getDay(),
			dateTime.getHours(),
			dateTime.getMinutes(),
			dateTime.getSeconds());
		return cal;
	}

	private void applySelectionListener() {
		dateTime.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
				applyEditorValueAndDeactivate();
			}
		});
	}

	private void applyTraversalListener() {
		dateTime.addTraverseListener(new TraverseListener() {
			@Override
			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_ESCAPE || e.detail == SWT.TRAVERSE_RETURN) {
					e.doit = false;
				}
			}
		});
	}

	private void applyFocusListener() {
		dateTime.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				TimeCellEditor.this.focusLost();
			}
		});
	}

	@Override
	protected void focusLost() {
		if (isActivated()) {
			applyEditorValueAndDeactivate();
		}
	}

	@Override
	protected void doSetFocus() {
		dateTime.setFocus();
	}

	@Override
	protected void doSetValue(Object value) {
		Calendar cal = (Calendar) value;
		dateTime.setYear(cal.get(Calendar.YEAR));
		dateTime.setMonth(cal.get(Calendar.MONTH));
		dateTime.setDay(cal.get(Calendar.DAY_OF_MONTH));
		dateTime.setHours(cal.get(Calendar.HOUR_OF_DAY));
		dateTime.setMinutes(cal.get(Calendar.MINUTE));
		dateTime.setSeconds(cal.get(Calendar.SECOND));
	}
}
