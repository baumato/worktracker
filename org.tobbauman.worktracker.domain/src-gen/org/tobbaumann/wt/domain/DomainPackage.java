/**
 */
package org.tobbaumann.wt.domain;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.tobbaumann.wt.domain.DomainFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel basePackage='org.tobbaumann.wt' editPluginID='org.tobbaumann.worktracker.domain.edit' editDirectory='/org.tobbaumann.worktracker.domain.edit/src' editorPluginID='org.tobbaumann.worktracker.domain.editor' editorDirectory='/org.tobbaumann.worktracker.domain.editor/src'"
 *        annotation="http://www.eclipse.org/emf/2011/Xcore Ecore='http://www.eclipse.org/emf/2002/Ecore' GenModel='http://www.eclipse.org/emf/2002/GenModel'"
 * @generated
 */
public interface DomainPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "domain";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "org.tobbaumann.wt.domain/1.0";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "domain";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  DomainPackage eINSTANCE = org.tobbaumann.wt.domain.impl.DomainPackageImpl.init();

  /**
   * The meta object id for the '{@link java.lang.Comparable<org.tobbaumann.wt.domain.WorkItem> <em>Comparable</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see java.lang.Comparable<org.tobbaumann.wt.domain.WorkItem>
   * @see org.tobbaumann.wt.domain.impl.DomainPackageImpl#getComparable()
   * @generated
   */
  int COMPARABLE = 0;

  /**
   * The number of structural features of the '<em>Comparable</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPARABLE_FEATURE_COUNT = 0;

  /**
   * The number of operations of the '<em>Comparable</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPARABLE_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link org.tobbaumann.wt.domain.impl.ActivityImpl <em>Activity</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.tobbaumann.wt.domain.impl.ActivityImpl
   * @see org.tobbaumann.wt.domain.impl.DomainPackageImpl#getActivity()
   * @generated
   */
  int ACTIVITY = 1;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ACTIVITY__ID = 0;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ACTIVITY__NAME = 1;

  /**
   * The feature id for the '<em><b>In Use</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ACTIVITY__IN_USE = 2;

  /**
   * The feature id for the '<em><b>Occurrence Frequency</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ACTIVITY__OCCURRENCE_FREQUENCY = 3;

  /**
   * The number of structural features of the '<em>Activity</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ACTIVITY_FEATURE_COUNT = 4;

  /**
   * The operation id for the '<em>Increment Occurrence Frequency</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ACTIVITY___INCREMENT_OCCURRENCE_FREQUENCY = 0;

  /**
   * The number of operations of the '<em>Activity</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ACTIVITY_OPERATION_COUNT = 1;

  /**
   * The meta object id for the '{@link org.tobbaumann.wt.domain.impl.WorkItemImpl <em>Work Item</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.tobbaumann.wt.domain.impl.WorkItemImpl
   * @see org.tobbaumann.wt.domain.impl.DomainPackageImpl#getWorkItem()
   * @generated
   */
  int WORK_ITEM = 2;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORK_ITEM__ID = COMPARABLE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Activity</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORK_ITEM__ACTIVITY = COMPARABLE_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Activity Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORK_ITEM__ACTIVITY_NAME = COMPARABLE_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Start</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORK_ITEM__START = COMPARABLE_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>End Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORK_ITEM__END_DATE = COMPARABLE_FEATURE_COUNT + 4;

  /**
   * The feature id for the '<em><b>End</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORK_ITEM__END = COMPARABLE_FEATURE_COUNT + 5;

  /**
   * The feature id for the '<em><b>Duration</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORK_ITEM__DURATION = COMPARABLE_FEATURE_COUNT + 6;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORK_ITEM__DESCRIPTION = COMPARABLE_FEATURE_COUNT + 7;

  /**
   * The number of structural features of the '<em>Work Item</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORK_ITEM_FEATURE_COUNT = COMPARABLE_FEATURE_COUNT + 8;

  /**
   * The operation id for the '<em>Format Start</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORK_ITEM___FORMAT_START__DATEFORMAT = COMPARABLE_OPERATION_COUNT + 0;

  /**
   * The operation id for the '<em>Get Date Part Of Start</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORK_ITEM___GET_DATE_PART_OF_START = COMPARABLE_OPERATION_COUNT + 1;

  /**
   * The operation id for the '<em>Format End</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORK_ITEM___FORMAT_END__DATEFORMAT = COMPARABLE_OPERATION_COUNT + 2;

  /**
   * The operation id for the '<em>Compare To</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORK_ITEM___COMPARE_TO__WORKITEM = COMPARABLE_OPERATION_COUNT + 3;

  /**
   * The number of operations of the '<em>Work Item</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORK_ITEM_OPERATION_COUNT = COMPARABLE_OPERATION_COUNT + 4;

  /**
   * The meta object id for the '{@link org.tobbaumann.wt.domain.impl.WorkItemSummaryImpl <em>Work Item Summary</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.tobbaumann.wt.domain.impl.WorkItemSummaryImpl
   * @see org.tobbaumann.wt.domain.impl.DomainPackageImpl#getWorkItemSummary()
   * @generated
   */
  int WORK_ITEM_SUMMARY = 3;

  /**
   * The feature id for the '<em><b>Work Items</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORK_ITEM_SUMMARY__WORK_ITEMS = 0;

  /**
   * The feature id for the '<em><b>Activity Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORK_ITEM_SUMMARY__ACTIVITY_NAME = 1;

  /**
   * The feature id for the '<em><b>Sum Of Durations</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORK_ITEM_SUMMARY__SUM_OF_DURATIONS = 2;

  /**
   * The feature id for the '<em><b>Sum Of Descriptions</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORK_ITEM_SUMMARY__SUM_OF_DESCRIPTIONS = 3;

  /**
   * The number of structural features of the '<em>Work Item Summary</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORK_ITEM_SUMMARY_FEATURE_COUNT = 4;

  /**
   * The number of operations of the '<em>Work Item Summary</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORK_ITEM_SUMMARY_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link org.tobbaumann.wt.domain.impl.TimeSpanImpl <em>Time Span</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.tobbaumann.wt.domain.impl.TimeSpanImpl
   * @see org.tobbaumann.wt.domain.impl.DomainPackageImpl#getTimeSpan()
   * @generated
   */
  int TIME_SPAN = 5;

  /**
   * The meta object id for the '{@link org.tobbaumann.wt.domain.impl.WorkItemSummariesImpl <em>Work Item Summaries</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.tobbaumann.wt.domain.impl.WorkItemSummariesImpl
   * @see org.tobbaumann.wt.domain.impl.DomainPackageImpl#getWorkItemSummaries()
   * @generated
   */
  int WORK_ITEM_SUMMARIES = 4;

  /**
   * The feature id for the '<em><b>Work Item Summaries</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORK_ITEM_SUMMARIES__WORK_ITEM_SUMMARIES = 0;

  /**
   * The feature id for the '<em><b>Sum Of Durations</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORK_ITEM_SUMMARIES__SUM_OF_DURATIONS = 1;

  /**
   * The number of structural features of the '<em>Work Item Summaries</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORK_ITEM_SUMMARIES_FEATURE_COUNT = 2;

  /**
   * The operation id for the '<em>Compute Duration Ratio In Percent</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORK_ITEM_SUMMARIES___COMPUTE_DURATION_RATIO_IN_PERCENT__WORKITEMSUMMARY = 0;

  /**
   * The operation id for the '<em>Compute Duration Ratio</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORK_ITEM_SUMMARIES___COMPUTE_DURATION_RATIO__WORKITEMSUMMARY_INT = 1;

  /**
   * The operation id for the '<em>Compute Sum Of Duration Ratio</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORK_ITEM_SUMMARIES___COMPUTE_SUM_OF_DURATION_RATIO__INT = 2;

  /**
   * The number of operations of the '<em>Work Item Summaries</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WORK_ITEM_SUMMARIES_OPERATION_COUNT = 3;

  /**
   * The feature id for the '<em><b>Millis</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TIME_SPAN__MILLIS = 0;

  /**
   * The feature id for the '<em><b>Seconds</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TIME_SPAN__SECONDS = 1;

  /**
   * The feature id for the '<em><b>Minutes</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TIME_SPAN__MINUTES = 2;

  /**
   * The feature id for the '<em><b>Hours</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TIME_SPAN__HOURS = 3;

  /**
   * The feature id for the '<em><b>Days</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TIME_SPAN__DAYS = 4;

  /**
   * The feature id for the '<em><b>Weeks</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TIME_SPAN__WEEKS = 5;

  /**
   * The number of structural features of the '<em>Time Span</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TIME_SPAN_FEATURE_COUNT = 6;

  /**
   * The operation id for the '<em>In Seconds</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TIME_SPAN___IN_SECONDS = 0;

  /**
   * The operation id for the '<em>In Minutes</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TIME_SPAN___IN_MINUTES = 1;

  /**
   * The operation id for the '<em>In Hours</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TIME_SPAN___IN_HOURS = 2;

  /**
   * The operation id for the '<em>In Days</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TIME_SPAN___IN_DAYS = 3;

  /**
   * The operation id for the '<em>In Weeks</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TIME_SPAN___IN_WEEKS = 4;

  /**
   * The operation id for the '<em>To String</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TIME_SPAN___TO_STRING = 5;

  /**
   * The number of operations of the '<em>Time Span</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TIME_SPAN_OPERATION_COUNT = 6;

  /**
   * The meta object id for the '<em>Date</em>' data type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see java.util.Date
   * @see org.tobbaumann.wt.domain.impl.DomainPackageImpl#getDate()
   * @generated
   */
  int DATE = 6;

  /**
   * The meta object id for the '<em>Date Format</em>' data type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see java.text.DateFormat
   * @see org.tobbaumann.wt.domain.impl.DomainPackageImpl#getDateFormat()
   * @generated
   */
  int DATE_FORMAT = 7;


  /**
   * Returns the meta object for class '{@link java.lang.Comparable<org.tobbaumann.wt.domain.WorkItem> <em>Comparable</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Comparable</em>'.
   * @see java.lang.Comparable<org.tobbaumann.wt.domain.WorkItem>
   * @model instanceClass="java.lang.Comparable<org.tobbaumann.wt.domain.WorkItem>"
   * @generated
   */
  EClass getComparable();

  /**
   * Returns the meta object for class '{@link org.tobbaumann.wt.domain.Activity <em>Activity</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Activity</em>'.
   * @see org.tobbaumann.wt.domain.Activity
   * @generated
   */
  EClass getActivity();

  /**
   * Returns the meta object for the attribute '{@link org.tobbaumann.wt.domain.Activity#getId <em>Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Id</em>'.
   * @see org.tobbaumann.wt.domain.Activity#getId()
   * @see #getActivity()
   * @generated
   */
  EAttribute getActivity_Id();

  /**
   * Returns the meta object for the attribute '{@link org.tobbaumann.wt.domain.Activity#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.tobbaumann.wt.domain.Activity#getName()
   * @see #getActivity()
   * @generated
   */
  EAttribute getActivity_Name();

  /**
   * Returns the meta object for the attribute '{@link org.tobbaumann.wt.domain.Activity#isInUse <em>In Use</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>In Use</em>'.
   * @see org.tobbaumann.wt.domain.Activity#isInUse()
   * @see #getActivity()
   * @generated
   */
  EAttribute getActivity_InUse();

  /**
   * Returns the meta object for the attribute '{@link org.tobbaumann.wt.domain.Activity#getOccurrenceFrequency <em>Occurrence Frequency</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Occurrence Frequency</em>'.
   * @see org.tobbaumann.wt.domain.Activity#getOccurrenceFrequency()
   * @see #getActivity()
   * @generated
   */
  EAttribute getActivity_OccurrenceFrequency();

  /**
   * Returns the meta object for the '{@link org.tobbaumann.wt.domain.Activity#incrementOccurrenceFrequency() <em>Increment Occurrence Frequency</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Increment Occurrence Frequency</em>' operation.
   * @see org.tobbaumann.wt.domain.Activity#incrementOccurrenceFrequency()
   * @generated
   */
  EOperation getActivity__IncrementOccurrenceFrequency();

  /**
   * Returns the meta object for class '{@link org.tobbaumann.wt.domain.WorkItem <em>Work Item</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Work Item</em>'.
   * @see org.tobbaumann.wt.domain.WorkItem
   * @generated
   */
  EClass getWorkItem();

  /**
   * Returns the meta object for the attribute '{@link org.tobbaumann.wt.domain.WorkItem#getId <em>Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Id</em>'.
   * @see org.tobbaumann.wt.domain.WorkItem#getId()
   * @see #getWorkItem()
   * @generated
   */
  EAttribute getWorkItem_Id();

  /**
   * Returns the meta object for the reference '{@link org.tobbaumann.wt.domain.WorkItem#getActivity <em>Activity</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Activity</em>'.
   * @see org.tobbaumann.wt.domain.WorkItem#getActivity()
   * @see #getWorkItem()
   * @generated
   */
  EReference getWorkItem_Activity();

  /**
   * Returns the meta object for the attribute '{@link org.tobbaumann.wt.domain.WorkItem#getActivityName <em>Activity Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Activity Name</em>'.
   * @see org.tobbaumann.wt.domain.WorkItem#getActivityName()
   * @see #getWorkItem()
   * @generated
   */
  EAttribute getWorkItem_ActivityName();

  /**
   * Returns the meta object for the attribute '{@link org.tobbaumann.wt.domain.WorkItem#getStart <em>Start</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Start</em>'.
   * @see org.tobbaumann.wt.domain.WorkItem#getStart()
   * @see #getWorkItem()
   * @generated
   */
  EAttribute getWorkItem_Start();

  /**
   * Returns the meta object for the attribute '{@link org.tobbaumann.wt.domain.WorkItem#getEndDate <em>End Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>End Date</em>'.
   * @see org.tobbaumann.wt.domain.WorkItem#getEndDate()
   * @see #getWorkItem()
   * @generated
   */
  EAttribute getWorkItem_EndDate();

  /**
   * Returns the meta object for the attribute '{@link org.tobbaumann.wt.domain.WorkItem#getEnd <em>End</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>End</em>'.
   * @see org.tobbaumann.wt.domain.WorkItem#getEnd()
   * @see #getWorkItem()
   * @generated
   */
  EAttribute getWorkItem_End();

  /**
   * Returns the meta object for the containment reference '{@link org.tobbaumann.wt.domain.WorkItem#getDuration <em>Duration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Duration</em>'.
   * @see org.tobbaumann.wt.domain.WorkItem#getDuration()
   * @see #getWorkItem()
   * @generated
   */
  EReference getWorkItem_Duration();

  /**
   * Returns the meta object for the attribute '{@link org.tobbaumann.wt.domain.WorkItem#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see org.tobbaumann.wt.domain.WorkItem#getDescription()
   * @see #getWorkItem()
   * @generated
   */
  EAttribute getWorkItem_Description();

  /**
   * Returns the meta object for the '{@link org.tobbaumann.wt.domain.WorkItem#formatStart(java.text.DateFormat) <em>Format Start</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Format Start</em>' operation.
   * @see org.tobbaumann.wt.domain.WorkItem#formatStart(java.text.DateFormat)
   * @generated
   */
  EOperation getWorkItem__FormatStart__DateFormat();

  /**
   * Returns the meta object for the '{@link org.tobbaumann.wt.domain.WorkItem#getDatePartOfStart() <em>Get Date Part Of Start</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Date Part Of Start</em>' operation.
   * @see org.tobbaumann.wt.domain.WorkItem#getDatePartOfStart()
   * @generated
   */
  EOperation getWorkItem__GetDatePartOfStart();

  /**
   * Returns the meta object for the '{@link org.tobbaumann.wt.domain.WorkItem#formatEnd(java.text.DateFormat) <em>Format End</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Format End</em>' operation.
   * @see org.tobbaumann.wt.domain.WorkItem#formatEnd(java.text.DateFormat)
   * @generated
   */
  EOperation getWorkItem__FormatEnd__DateFormat();

  /**
   * Returns the meta object for the '{@link org.tobbaumann.wt.domain.WorkItem#compareTo(org.tobbaumann.wt.domain.WorkItem) <em>Compare To</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Compare To</em>' operation.
   * @see org.tobbaumann.wt.domain.WorkItem#compareTo(org.tobbaumann.wt.domain.WorkItem)
   * @generated
   */
  EOperation getWorkItem__CompareTo__WorkItem();

  /**
   * Returns the meta object for class '{@link org.tobbaumann.wt.domain.WorkItemSummary <em>Work Item Summary</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Work Item Summary</em>'.
   * @see org.tobbaumann.wt.domain.WorkItemSummary
   * @generated
   */
  EClass getWorkItemSummary();

  /**
   * Returns the meta object for the reference list '{@link org.tobbaumann.wt.domain.WorkItemSummary#getWorkItems <em>Work Items</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Work Items</em>'.
   * @see org.tobbaumann.wt.domain.WorkItemSummary#getWorkItems()
   * @see #getWorkItemSummary()
   * @generated
   */
  EReference getWorkItemSummary_WorkItems();

  /**
   * Returns the meta object for the attribute '{@link org.tobbaumann.wt.domain.WorkItemSummary#getActivityName <em>Activity Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Activity Name</em>'.
   * @see org.tobbaumann.wt.domain.WorkItemSummary#getActivityName()
   * @see #getWorkItemSummary()
   * @generated
   */
  EAttribute getWorkItemSummary_ActivityName();

  /**
   * Returns the meta object for the containment reference '{@link org.tobbaumann.wt.domain.WorkItemSummary#getSumOfDurations <em>Sum Of Durations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Sum Of Durations</em>'.
   * @see org.tobbaumann.wt.domain.WorkItemSummary#getSumOfDurations()
   * @see #getWorkItemSummary()
   * @generated
   */
  EReference getWorkItemSummary_SumOfDurations();

  /**
   * Returns the meta object for the attribute list '{@link org.tobbaumann.wt.domain.WorkItemSummary#getSumOfDescriptions <em>Sum Of Descriptions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Sum Of Descriptions</em>'.
   * @see org.tobbaumann.wt.domain.WorkItemSummary#getSumOfDescriptions()
   * @see #getWorkItemSummary()
   * @generated
   */
  EAttribute getWorkItemSummary_SumOfDescriptions();

  /**
   * Returns the meta object for class '{@link org.tobbaumann.wt.domain.TimeSpan <em>Time Span</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Time Span</em>'.
   * @see org.tobbaumann.wt.domain.TimeSpan
   * @generated
   */
  EClass getTimeSpan();

  /**
   * Returns the meta object for the attribute '{@link org.tobbaumann.wt.domain.TimeSpan#getMillis <em>Millis</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Millis</em>'.
   * @see org.tobbaumann.wt.domain.TimeSpan#getMillis()
   * @see #getTimeSpan()
   * @generated
   */
  EAttribute getTimeSpan_Millis();

  /**
   * Returns the meta object for the attribute '{@link org.tobbaumann.wt.domain.TimeSpan#getSeconds <em>Seconds</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Seconds</em>'.
   * @see org.tobbaumann.wt.domain.TimeSpan#getSeconds()
   * @see #getTimeSpan()
   * @generated
   */
  EAttribute getTimeSpan_Seconds();

  /**
   * Returns the meta object for the attribute '{@link org.tobbaumann.wt.domain.TimeSpan#getMinutes <em>Minutes</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Minutes</em>'.
   * @see org.tobbaumann.wt.domain.TimeSpan#getMinutes()
   * @see #getTimeSpan()
   * @generated
   */
  EAttribute getTimeSpan_Minutes();

  /**
   * Returns the meta object for the attribute '{@link org.tobbaumann.wt.domain.TimeSpan#getHours <em>Hours</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Hours</em>'.
   * @see org.tobbaumann.wt.domain.TimeSpan#getHours()
   * @see #getTimeSpan()
   * @generated
   */
  EAttribute getTimeSpan_Hours();

  /**
   * Returns the meta object for the attribute '{@link org.tobbaumann.wt.domain.TimeSpan#getDays <em>Days</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Days</em>'.
   * @see org.tobbaumann.wt.domain.TimeSpan#getDays()
   * @see #getTimeSpan()
   * @generated
   */
  EAttribute getTimeSpan_Days();

  /**
   * Returns the meta object for the attribute '{@link org.tobbaumann.wt.domain.TimeSpan#getWeeks <em>Weeks</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Weeks</em>'.
   * @see org.tobbaumann.wt.domain.TimeSpan#getWeeks()
   * @see #getTimeSpan()
   * @generated
   */
  EAttribute getTimeSpan_Weeks();

  /**
   * Returns the meta object for the '{@link org.tobbaumann.wt.domain.TimeSpan#inSeconds() <em>In Seconds</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>In Seconds</em>' operation.
   * @see org.tobbaumann.wt.domain.TimeSpan#inSeconds()
   * @generated
   */
  EOperation getTimeSpan__InSeconds();

  /**
   * Returns the meta object for the '{@link org.tobbaumann.wt.domain.TimeSpan#inMinutes() <em>In Minutes</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>In Minutes</em>' operation.
   * @see org.tobbaumann.wt.domain.TimeSpan#inMinutes()
   * @generated
   */
  EOperation getTimeSpan__InMinutes();

  /**
   * Returns the meta object for the '{@link org.tobbaumann.wt.domain.TimeSpan#inHours() <em>In Hours</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>In Hours</em>' operation.
   * @see org.tobbaumann.wt.domain.TimeSpan#inHours()
   * @generated
   */
  EOperation getTimeSpan__InHours();

  /**
   * Returns the meta object for the '{@link org.tobbaumann.wt.domain.TimeSpan#inDays() <em>In Days</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>In Days</em>' operation.
   * @see org.tobbaumann.wt.domain.TimeSpan#inDays()
   * @generated
   */
  EOperation getTimeSpan__InDays();

  /**
   * Returns the meta object for the '{@link org.tobbaumann.wt.domain.TimeSpan#inWeeks() <em>In Weeks</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>In Weeks</em>' operation.
   * @see org.tobbaumann.wt.domain.TimeSpan#inWeeks()
   * @generated
   */
  EOperation getTimeSpan__InWeeks();

  /**
   * Returns the meta object for the '{@link org.tobbaumann.wt.domain.TimeSpan#toString() <em>To String</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>To String</em>' operation.
   * @see org.tobbaumann.wt.domain.TimeSpan#toString()
   * @generated
   */
  EOperation getTimeSpan__ToString();

  /**
   * Returns the meta object for class '{@link org.tobbaumann.wt.domain.WorkItemSummaries <em>Work Item Summaries</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Work Item Summaries</em>'.
   * @see org.tobbaumann.wt.domain.WorkItemSummaries
   * @generated
   */
  EClass getWorkItemSummaries();

  /**
   * Returns the meta object for the reference list '{@link org.tobbaumann.wt.domain.WorkItemSummaries#getWorkItemSummaries <em>Work Item Summaries</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Work Item Summaries</em>'.
   * @see org.tobbaumann.wt.domain.WorkItemSummaries#getWorkItemSummaries()
   * @see #getWorkItemSummaries()
   * @generated
   */
  EReference getWorkItemSummaries_WorkItemSummaries();

  /**
   * Returns the meta object for the containment reference '{@link org.tobbaumann.wt.domain.WorkItemSummaries#getSumOfDurations <em>Sum Of Durations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Sum Of Durations</em>'.
   * @see org.tobbaumann.wt.domain.WorkItemSummaries#getSumOfDurations()
   * @see #getWorkItemSummaries()
   * @generated
   */
  EReference getWorkItemSummaries_SumOfDurations();

  /**
   * Returns the meta object for the '{@link org.tobbaumann.wt.domain.WorkItemSummaries#computeDurationRatioInPercent(org.tobbaumann.wt.domain.WorkItemSummary) <em>Compute Duration Ratio In Percent</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Compute Duration Ratio In Percent</em>' operation.
   * @see org.tobbaumann.wt.domain.WorkItemSummaries#computeDurationRatioInPercent(org.tobbaumann.wt.domain.WorkItemSummary)
   * @generated
   */
  EOperation getWorkItemSummaries__ComputeDurationRatioInPercent__WorkItemSummary();

  /**
   * Returns the meta object for the '{@link org.tobbaumann.wt.domain.WorkItemSummaries#computeDurationRatio(org.tobbaumann.wt.domain.WorkItemSummary, int) <em>Compute Duration Ratio</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Compute Duration Ratio</em>' operation.
   * @see org.tobbaumann.wt.domain.WorkItemSummaries#computeDurationRatio(org.tobbaumann.wt.domain.WorkItemSummary, int)
   * @generated
   */
  EOperation getWorkItemSummaries__ComputeDurationRatio__WorkItemSummary_int();

  /**
   * Returns the meta object for the '{@link org.tobbaumann.wt.domain.WorkItemSummaries#computeSumOfDurationRatio(int) <em>Compute Sum Of Duration Ratio</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Compute Sum Of Duration Ratio</em>' operation.
   * @see org.tobbaumann.wt.domain.WorkItemSummaries#computeSumOfDurationRatio(int)
   * @generated
   */
  EOperation getWorkItemSummaries__ComputeSumOfDurationRatio__int();

  /**
   * Returns the meta object for data type '{@link java.util.Date <em>Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for data type '<em>Date</em>'.
   * @see java.util.Date
   * @model instanceClass="java.util.Date"
   * @generated
   */
  EDataType getDate();

  /**
   * Returns the meta object for data type '{@link java.text.DateFormat <em>Date Format</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for data type '<em>Date Format</em>'.
   * @see java.text.DateFormat
   * @model instanceClass="java.text.DateFormat"
   * @generated
   */
  EDataType getDateFormat();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  DomainFactory getDomainFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each operation of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link java.lang.Comparable<org.tobbaumann.wt.domain.WorkItem> <em>Comparable</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.Comparable<org.tobbaumann.wt.domain.WorkItem>
     * @see org.tobbaumann.wt.domain.impl.DomainPackageImpl#getComparable()
     * @generated
     */
    EClass COMPARABLE = eINSTANCE.getComparable();

    /**
     * The meta object literal for the '{@link org.tobbaumann.wt.domain.impl.ActivityImpl <em>Activity</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.tobbaumann.wt.domain.impl.ActivityImpl
     * @see org.tobbaumann.wt.domain.impl.DomainPackageImpl#getActivity()
     * @generated
     */
    EClass ACTIVITY = eINSTANCE.getActivity();

    /**
     * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ACTIVITY__ID = eINSTANCE.getActivity_Id();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ACTIVITY__NAME = eINSTANCE.getActivity_Name();

    /**
     * The meta object literal for the '<em><b>In Use</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ACTIVITY__IN_USE = eINSTANCE.getActivity_InUse();

    /**
     * The meta object literal for the '<em><b>Occurrence Frequency</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ACTIVITY__OCCURRENCE_FREQUENCY = eINSTANCE.getActivity_OccurrenceFrequency();

    /**
     * The meta object literal for the '<em><b>Increment Occurrence Frequency</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation ACTIVITY___INCREMENT_OCCURRENCE_FREQUENCY = eINSTANCE.getActivity__IncrementOccurrenceFrequency();

    /**
     * The meta object literal for the '{@link org.tobbaumann.wt.domain.impl.WorkItemImpl <em>Work Item</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.tobbaumann.wt.domain.impl.WorkItemImpl
     * @see org.tobbaumann.wt.domain.impl.DomainPackageImpl#getWorkItem()
     * @generated
     */
    EClass WORK_ITEM = eINSTANCE.getWorkItem();

    /**
     * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute WORK_ITEM__ID = eINSTANCE.getWorkItem_Id();

    /**
     * The meta object literal for the '<em><b>Activity</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference WORK_ITEM__ACTIVITY = eINSTANCE.getWorkItem_Activity();

    /**
     * The meta object literal for the '<em><b>Activity Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute WORK_ITEM__ACTIVITY_NAME = eINSTANCE.getWorkItem_ActivityName();

    /**
     * The meta object literal for the '<em><b>Start</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute WORK_ITEM__START = eINSTANCE.getWorkItem_Start();

    /**
     * The meta object literal for the '<em><b>End Date</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute WORK_ITEM__END_DATE = eINSTANCE.getWorkItem_EndDate();

    /**
     * The meta object literal for the '<em><b>End</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute WORK_ITEM__END = eINSTANCE.getWorkItem_End();

    /**
     * The meta object literal for the '<em><b>Duration</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference WORK_ITEM__DURATION = eINSTANCE.getWorkItem_Duration();

    /**
     * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute WORK_ITEM__DESCRIPTION = eINSTANCE.getWorkItem_Description();

    /**
     * The meta object literal for the '<em><b>Format Start</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation WORK_ITEM___FORMAT_START__DATEFORMAT = eINSTANCE.getWorkItem__FormatStart__DateFormat();

    /**
     * The meta object literal for the '<em><b>Get Date Part Of Start</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation WORK_ITEM___GET_DATE_PART_OF_START = eINSTANCE.getWorkItem__GetDatePartOfStart();

    /**
     * The meta object literal for the '<em><b>Format End</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation WORK_ITEM___FORMAT_END__DATEFORMAT = eINSTANCE.getWorkItem__FormatEnd__DateFormat();

    /**
     * The meta object literal for the '<em><b>Compare To</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation WORK_ITEM___COMPARE_TO__WORKITEM = eINSTANCE.getWorkItem__CompareTo__WorkItem();

    /**
     * The meta object literal for the '{@link org.tobbaumann.wt.domain.impl.WorkItemSummaryImpl <em>Work Item Summary</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.tobbaumann.wt.domain.impl.WorkItemSummaryImpl
     * @see org.tobbaumann.wt.domain.impl.DomainPackageImpl#getWorkItemSummary()
     * @generated
     */
    EClass WORK_ITEM_SUMMARY = eINSTANCE.getWorkItemSummary();

    /**
     * The meta object literal for the '<em><b>Work Items</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference WORK_ITEM_SUMMARY__WORK_ITEMS = eINSTANCE.getWorkItemSummary_WorkItems();

    /**
     * The meta object literal for the '<em><b>Activity Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute WORK_ITEM_SUMMARY__ACTIVITY_NAME = eINSTANCE.getWorkItemSummary_ActivityName();

    /**
     * The meta object literal for the '<em><b>Sum Of Durations</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference WORK_ITEM_SUMMARY__SUM_OF_DURATIONS = eINSTANCE.getWorkItemSummary_SumOfDurations();

    /**
     * The meta object literal for the '<em><b>Sum Of Descriptions</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute WORK_ITEM_SUMMARY__SUM_OF_DESCRIPTIONS = eINSTANCE.getWorkItemSummary_SumOfDescriptions();

    /**
     * The meta object literal for the '{@link org.tobbaumann.wt.domain.impl.TimeSpanImpl <em>Time Span</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.tobbaumann.wt.domain.impl.TimeSpanImpl
     * @see org.tobbaumann.wt.domain.impl.DomainPackageImpl#getTimeSpan()
     * @generated
     */
    EClass TIME_SPAN = eINSTANCE.getTimeSpan();

    /**
     * The meta object literal for the '<em><b>Millis</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TIME_SPAN__MILLIS = eINSTANCE.getTimeSpan_Millis();

    /**
     * The meta object literal for the '<em><b>Seconds</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TIME_SPAN__SECONDS = eINSTANCE.getTimeSpan_Seconds();

    /**
     * The meta object literal for the '<em><b>Minutes</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TIME_SPAN__MINUTES = eINSTANCE.getTimeSpan_Minutes();

    /**
     * The meta object literal for the '<em><b>Hours</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TIME_SPAN__HOURS = eINSTANCE.getTimeSpan_Hours();

    /**
     * The meta object literal for the '<em><b>Days</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TIME_SPAN__DAYS = eINSTANCE.getTimeSpan_Days();

    /**
     * The meta object literal for the '<em><b>Weeks</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TIME_SPAN__WEEKS = eINSTANCE.getTimeSpan_Weeks();

    /**
     * The meta object literal for the '<em><b>In Seconds</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation TIME_SPAN___IN_SECONDS = eINSTANCE.getTimeSpan__InSeconds();

    /**
     * The meta object literal for the '<em><b>In Minutes</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation TIME_SPAN___IN_MINUTES = eINSTANCE.getTimeSpan__InMinutes();

    /**
     * The meta object literal for the '<em><b>In Hours</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation TIME_SPAN___IN_HOURS = eINSTANCE.getTimeSpan__InHours();

    /**
     * The meta object literal for the '<em><b>In Days</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation TIME_SPAN___IN_DAYS = eINSTANCE.getTimeSpan__InDays();

    /**
     * The meta object literal for the '<em><b>In Weeks</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation TIME_SPAN___IN_WEEKS = eINSTANCE.getTimeSpan__InWeeks();

    /**
     * The meta object literal for the '<em><b>To String</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation TIME_SPAN___TO_STRING = eINSTANCE.getTimeSpan__ToString();

    /**
     * The meta object literal for the '{@link org.tobbaumann.wt.domain.impl.WorkItemSummariesImpl <em>Work Item Summaries</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.tobbaumann.wt.domain.impl.WorkItemSummariesImpl
     * @see org.tobbaumann.wt.domain.impl.DomainPackageImpl#getWorkItemSummaries()
     * @generated
     */
    EClass WORK_ITEM_SUMMARIES = eINSTANCE.getWorkItemSummaries();

    /**
     * The meta object literal for the '<em><b>Work Item Summaries</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference WORK_ITEM_SUMMARIES__WORK_ITEM_SUMMARIES = eINSTANCE.getWorkItemSummaries_WorkItemSummaries();

    /**
     * The meta object literal for the '<em><b>Sum Of Durations</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference WORK_ITEM_SUMMARIES__SUM_OF_DURATIONS = eINSTANCE.getWorkItemSummaries_SumOfDurations();

    /**
     * The meta object literal for the '<em><b>Compute Duration Ratio In Percent</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation WORK_ITEM_SUMMARIES___COMPUTE_DURATION_RATIO_IN_PERCENT__WORKITEMSUMMARY = eINSTANCE.getWorkItemSummaries__ComputeDurationRatioInPercent__WorkItemSummary();

    /**
     * The meta object literal for the '<em><b>Compute Duration Ratio</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation WORK_ITEM_SUMMARIES___COMPUTE_DURATION_RATIO__WORKITEMSUMMARY_INT = eINSTANCE.getWorkItemSummaries__ComputeDurationRatio__WorkItemSummary_int();

    /**
     * The meta object literal for the '<em><b>Compute Sum Of Duration Ratio</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation WORK_ITEM_SUMMARIES___COMPUTE_SUM_OF_DURATION_RATIO__INT = eINSTANCE.getWorkItemSummaries__ComputeSumOfDurationRatio__int();

    /**
     * The meta object literal for the '<em>Date</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.util.Date
     * @see org.tobbaumann.wt.domain.impl.DomainPackageImpl#getDate()
     * @generated
     */
    EDataType DATE = eINSTANCE.getDate();

    /**
     * The meta object literal for the '<em>Date Format</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.text.DateFormat
     * @see org.tobbaumann.wt.domain.impl.DomainPackageImpl#getDateFormat()
     * @generated
     */
    EDataType DATE_FORMAT = eINSTANCE.getDateFormat();

  }

} //DomainPackage
