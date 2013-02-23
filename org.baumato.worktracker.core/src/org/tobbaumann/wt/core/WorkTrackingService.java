package org.tobbaumann.wt.core;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.tobbaumann.wt.domain.WorkItem;

public interface WorkTrackingService {

	void createWorkItems(Iterable<WorkItem> workItems);

	void updateWorkItems(Iterable<WorkItem> workItems);

	void deleteWorkItems(Iterable<WorkItem> workItems);

	WorkItem readWorkItem(String id);

	List<WorkItem> readWorkItems(Date date);

	List<WorkItem> readWorkItems();

	Set<String> readDates();
}
