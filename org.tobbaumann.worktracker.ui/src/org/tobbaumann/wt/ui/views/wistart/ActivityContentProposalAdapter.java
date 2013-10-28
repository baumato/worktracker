package org.tobbaumann.wt.ui.views.wistart;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;

import org.eclipse.core.databinding.observable.list.IListChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.ListChangeEvent;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Text;
import org.tobbaumann.wt.core.WorkTrackingService;
import org.tobbaumann.wt.domain.Activity;

/**
 *
 * @author tobbaumann
 *
 */
public class ActivityContentProposalAdapter extends ContentProposalAdapter {

	public static ActivityContentProposalAdapter applyContentAssist(Text txt, WorkTrackingService service) {
		return new ActivityContentProposalAdapter(txt, service);
	}

	private ActivityContentProposalAdapter(Text txt, WorkTrackingService service) {
		super(txt, new TextContentAdapter(), new ActivityContentProposalProvider(txt, service), null, null);
		setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
	}


	/**
	 *
	 * @author tobbaumann
	 *
	 */
	private static final class ActivityContentProposalProvider extends SimpleContentProposalProvider {

		ActivityContentProposalProvider(Text txt, WorkTrackingService service) {
			super(createProposalsFromActivities(service.getActivities()));
			setFiltering(true);
			updateProposalsOnActivityListChange(txt, service);
		}

		private static String[] createProposalsFromActivities(IObservableList<Activity> activities) {
			Collection<String> activityNames = newArrayList();
			for (Object o : activities) {
				Activity a = (Activity) o;
				activityNames.add(a.getName());
			}
			String[] arrActivityNames = activityNames.toArray(new String[0]);
			return arrActivityNames;
		}

		private void updateProposalsOnActivityListChange(Text text, WorkTrackingService service) {
			final IObservableList<Activity> activities = service.getActivities();
			final IListChangeListener<Activity> listChangeListener = new IListChangeListener<Activity>() {
				@Override
				public void handleListChange(ListChangeEvent<Activity> event) {
					String[] arrActivityNames = createProposalsFromActivities(activities);
					setProposals(arrActivityNames);
				}
			};
			activities.addListChangeListener(listChangeListener);
			text.addDisposeListener(new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					activities.removeListChangeListener(listChangeListener);
				}
			});
		}
	}
}
