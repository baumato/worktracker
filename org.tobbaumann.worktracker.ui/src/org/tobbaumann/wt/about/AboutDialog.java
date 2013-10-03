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
package org.tobbaumann.wt.about;

import java.net.URI;
import java.net.URL;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

@Creatable
public class AboutDialog extends Dialog {

	private static final String ABOUT_IMAGE_ID = AboutDialog.class.getName() + ".image";

	@Inject
	public AboutDialog(Shell shell) {
		super(shell);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("About");
	}

	@Override
	protected Control createDialogArea(Composite parentComposite) {
		Control area = super.createDialogArea(parentComposite);
		Composite parent = new Composite((Composite) area, SWT.NONE);
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		parent.setLayout(new GridLayout(2, false));

		CLabel lblImage = new CLabel(parent, SWT.CENTER);
		lblImage.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		lblImage.setImage(createImage());

		CLabel lblTxt = new CLabel(parent, SWT.CENTER);
		lblTxt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		lblTxt.setText(createText());

		return area;
	}

	private Image createImage() {
		try {
			Image image = JFaceResources.getImageRegistry().get(ABOUT_IMAGE_ID);
			if (image == null) {
				String strImgUrl = "platform:/plugin/org.tobbaumann.worktracker.ui/icons/pc.de/app-icon/finished-work-48.png";
				URL imgUrl = URI.create(strImgUrl).toURL();
				ImageDescriptor id = ImageDescriptor.createFromURL(imgUrl);
				JFaceResources.getImageRegistry().put(ABOUT_IMAGE_ID, id);
				image = createImage();
			}
			return image;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String createText() {
		try {
			return Resources.toString(
				Resources.getResource(AboutDialog.class, "about.txt"),
				Charsets.UTF_8);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
	}
}
