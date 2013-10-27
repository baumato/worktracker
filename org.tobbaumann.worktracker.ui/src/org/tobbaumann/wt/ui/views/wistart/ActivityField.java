package org.tobbaumann.wt.ui.views.wistart;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;

import org.eclipse.core.databinding.observable.list.IListChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.ListChangeEvent;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.domain.Activity;

/**
 *
 * @author tobbaumann
 *
 */
public class ActivityField extends Composite {

	private Text txtActivity;
	private ContentProposalAdapter activiyContentProposalAdapter;
	private WorkTrackingService service;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ActivityField(Composite parent, WorkTrackingService service) {
		super(parent, SWT.NONE);
		this.service = service;
		setLayout(new FillLayout(SWT.HORIZONTAL));
		createContent();
	}

	private void createContent() {
		txtActivity = new Text(this, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		applyContentAssist(txtActivity);
	}

	private void applyContentAssist(Text txtActivity) {
		SimpleContentProposalProvider proposalProvider = new SimpleContentProposalProvider(
				createProposalsFromActivities(service.getActivities()));
		activiyContentProposalAdapter = new ContentProposalAdapter(
			txtActivity,
			new TextContentAdapter(),
			proposalProvider,
			null,
			null);
		proposalProvider.setFiltering(true);
		activiyContentProposalAdapter.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
		updateProposalsOnActivityListChange(proposalProvider);
	}

	private String[] createProposalsFromActivities(IObservableList<Activity> activities) {
		Collection<String> activityNames = newArrayList();
		for (Object o : activities) {
			Activity a = (Activity) o;
			activityNames.add(a.getName());
		}
		String[] arrActivityNames = activityNames.toArray(new String[0]);
		return arrActivityNames;
	}

	private void updateProposalsOnActivityListChange(final SimpleContentProposalProvider proposalProvider) {
		final IObservableList<Activity> activities = service.getActivities();
		activities.addListChangeListener(new IListChangeListener<Activity>() {
			@Override
			public void handleListChange(ListChangeEvent<Activity> event) {
				String[] arrActivityNames = createProposalsFromActivities(activities);
				proposalProvider.setProposals(arrActivityNames);
			}
		});
	}

	public String getText() {
		return txtActivity.getText();
	}

	public void setText(String txt) {
		txtActivity.setText(txt);
	}

	public void addModifyListener(ModifyListener listener) {
		txtActivity.addModifyListener(listener);
	}

	@Override
	public void addKeyListener(KeyListener listener) {
		txtActivity.addKeyListener(listener);
	}

	@Override
	public void addFocusListener(FocusListener listener) {
		txtActivity.addFocusListener(listener);
	}

	public boolean isProposalPopupOpen() {
		return activiyContentProposalAdapter != null && activiyContentProposalAdapter.isProposalPopupOpen();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
