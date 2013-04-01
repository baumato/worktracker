/**
 */
package org.tobbaumann.wt.domain.impl;

import java.lang.Comparable;

import java.text.DateFormat;

import java.util.Date;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.tobbaumann.wt.domain.Activity;
import org.tobbaumann.wt.domain.DomainFactory;
import org.tobbaumann.wt.domain.DomainPackage;
import org.tobbaumann.wt.domain.TimeSpan;
import org.tobbaumann.wt.domain.WorkItem;
import org.tobbaumann.wt.domain.WorkItemSummary;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DomainPackageImpl extends EPackageImpl implements DomainPackage
{
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass comparableEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass activityEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass workItemEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass workItemSummaryEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass timeSpanEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EDataType dateEDataType = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EDataType dateFormatEDataType = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see org.tobbaumann.wt.domain.DomainPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private DomainPackageImpl()
  {
    super(eNS_URI, DomainFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   * 
   * <p>This method is used to initialize {@link DomainPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static DomainPackage init()
  {
    if (isInited) return (DomainPackage)EPackage.Registry.INSTANCE.getEPackage(DomainPackage.eNS_URI);

    // Obtain or create and register package
    DomainPackageImpl theDomainPackage = (DomainPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof DomainPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new DomainPackageImpl());

    isInited = true;

    // Initialize simple dependencies
    EcorePackage.eINSTANCE.eClass();

    // Create package meta-data objects
    theDomainPackage.createPackageContents();

    // Initialize created meta-data
    theDomainPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theDomainPackage.freeze();

  
    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(DomainPackage.eNS_URI, theDomainPackage);
    return theDomainPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getComparable()
  {
    return comparableEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getActivity()
  {
    return activityEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getActivity_Id()
  {
    return (EAttribute)activityEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getActivity_Name()
  {
    return (EAttribute)activityEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getActivity_InUse()
  {
    return (EAttribute)activityEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getActivity_OccurrenceFrequency()
  {
    return (EAttribute)activityEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getActivity__IncrementOccurrenceFrequency()
  {
    return activityEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getWorkItem()
  {
    return workItemEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getWorkItem_Id()
  {
    return (EAttribute)workItemEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getWorkItem_Activity()
  {
    return (EReference)workItemEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getWorkItem_ActivityName()
  {
    return (EAttribute)workItemEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getWorkItem_Start()
  {
    return (EAttribute)workItemEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getWorkItem_EndDate()
  {
    return (EAttribute)workItemEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getWorkItem_End()
  {
    return (EAttribute)workItemEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getWorkItem_Duration()
  {
    return (EReference)workItemEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getWorkItem_Description()
  {
    return (EAttribute)workItemEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getWorkItem__FormatStart__DateFormat()
  {
    return workItemEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getWorkItem__FormatEnd__DateFormat()
  {
    return workItemEClass.getEOperations().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getWorkItem__CompareTo__WorkItem()
  {
    return workItemEClass.getEOperations().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getWorkItemSummary()
  {
    return workItemSummaryEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getWorkItemSummary_WorkItems()
  {
    return (EReference)workItemSummaryEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getWorkItemSummary_ActivityName()
  {
    return (EAttribute)workItemSummaryEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getWorkItemSummary_SumOfDurations()
  {
    return (EReference)workItemSummaryEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getWorkItemSummary_SumOfDescriptions()
  {
    return (EAttribute)workItemSummaryEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getTimeSpan()
  {
    return timeSpanEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getTimeSpan_Millis()
  {
    return (EAttribute)timeSpanEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getTimeSpan_Seconds()
  {
    return (EAttribute)timeSpanEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getTimeSpan_Minutes()
  {
    return (EAttribute)timeSpanEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getTimeSpan_Hours()
  {
    return (EAttribute)timeSpanEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getTimeSpan_Days()
  {
    return (EAttribute)timeSpanEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getTimeSpan_Weeks()
  {
    return (EAttribute)timeSpanEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getTimeSpan__InSeconds()
  {
    return timeSpanEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getTimeSpan__InMinutes()
  {
    return timeSpanEClass.getEOperations().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getTimeSpan__InHours()
  {
    return timeSpanEClass.getEOperations().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getTimeSpan__InDays()
  {
    return timeSpanEClass.getEOperations().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getTimeSpan__InWeeks()
  {
    return timeSpanEClass.getEOperations().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getTimeSpan__ToString()
  {
    return timeSpanEClass.getEOperations().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EDataType getDate()
  {
    return dateEDataType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EDataType getDateFormat()
  {
    return dateFormatEDataType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DomainFactory getDomainFactory()
  {
    return (DomainFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents()
  {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    comparableEClass = createEClass(COMPARABLE);

    activityEClass = createEClass(ACTIVITY);
    createEAttribute(activityEClass, ACTIVITY__ID);
    createEAttribute(activityEClass, ACTIVITY__NAME);
    createEAttribute(activityEClass, ACTIVITY__IN_USE);
    createEAttribute(activityEClass, ACTIVITY__OCCURRENCE_FREQUENCY);
    createEOperation(activityEClass, ACTIVITY___INCREMENT_OCCURRENCE_FREQUENCY);

    workItemEClass = createEClass(WORK_ITEM);
    createEAttribute(workItemEClass, WORK_ITEM__ID);
    createEReference(workItemEClass, WORK_ITEM__ACTIVITY);
    createEAttribute(workItemEClass, WORK_ITEM__ACTIVITY_NAME);
    createEAttribute(workItemEClass, WORK_ITEM__START);
    createEAttribute(workItemEClass, WORK_ITEM__END_DATE);
    createEAttribute(workItemEClass, WORK_ITEM__END);
    createEReference(workItemEClass, WORK_ITEM__DURATION);
    createEAttribute(workItemEClass, WORK_ITEM__DESCRIPTION);
    createEOperation(workItemEClass, WORK_ITEM___FORMAT_START__DATEFORMAT);
    createEOperation(workItemEClass, WORK_ITEM___FORMAT_END__DATEFORMAT);
    createEOperation(workItemEClass, WORK_ITEM___COMPARE_TO__WORKITEM);

    workItemSummaryEClass = createEClass(WORK_ITEM_SUMMARY);
    createEReference(workItemSummaryEClass, WORK_ITEM_SUMMARY__WORK_ITEMS);
    createEAttribute(workItemSummaryEClass, WORK_ITEM_SUMMARY__ACTIVITY_NAME);
    createEReference(workItemSummaryEClass, WORK_ITEM_SUMMARY__SUM_OF_DURATIONS);
    createEAttribute(workItemSummaryEClass, WORK_ITEM_SUMMARY__SUM_OF_DESCRIPTIONS);

    timeSpanEClass = createEClass(TIME_SPAN);
    createEAttribute(timeSpanEClass, TIME_SPAN__MILLIS);
    createEAttribute(timeSpanEClass, TIME_SPAN__SECONDS);
    createEAttribute(timeSpanEClass, TIME_SPAN__MINUTES);
    createEAttribute(timeSpanEClass, TIME_SPAN__HOURS);
    createEAttribute(timeSpanEClass, TIME_SPAN__DAYS);
    createEAttribute(timeSpanEClass, TIME_SPAN__WEEKS);
    createEOperation(timeSpanEClass, TIME_SPAN___IN_SECONDS);
    createEOperation(timeSpanEClass, TIME_SPAN___IN_MINUTES);
    createEOperation(timeSpanEClass, TIME_SPAN___IN_HOURS);
    createEOperation(timeSpanEClass, TIME_SPAN___IN_DAYS);
    createEOperation(timeSpanEClass, TIME_SPAN___IN_WEEKS);
    createEOperation(timeSpanEClass, TIME_SPAN___TO_STRING);

    // Create data types
    dateEDataType = createEDataType(DATE);
    dateFormatEDataType = createEDataType(DATE_FORMAT);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents()
  {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Obtain other dependent packages
    EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    workItemEClass.getESuperTypes().add(this.getComparable());

    // Initialize classes, features, and operations; add parameters
    initEClass(comparableEClass, Comparable.class, "Comparable", IS_ABSTRACT, IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS, "java.lang.Comparable<org.tobbaumann.wt.domain.WorkItem>");

    initEClass(activityEClass, Activity.class, "Activity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getActivity_Id(), theEcorePackage.getEString(), "id", null, 0, 1, Activity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getActivity_Name(), theEcorePackage.getEString(), "name", null, 0, 1, Activity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getActivity_InUse(), theEcorePackage.getEBoolean(), "inUse", null, 0, 1, Activity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getActivity_OccurrenceFrequency(), theEcorePackage.getELong(), "occurrenceFrequency", null, 0, 1, Activity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEOperation(getActivity__IncrementOccurrenceFrequency(), null, "incrementOccurrenceFrequency", 0, 1, !IS_UNIQUE, IS_ORDERED);

    initEClass(workItemEClass, WorkItem.class, "WorkItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getWorkItem_Id(), theEcorePackage.getEString(), "id", null, 0, 1, WorkItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getWorkItem_Activity(), this.getActivity(), null, "activity", null, 0, 1, WorkItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getWorkItem_ActivityName(), theEcorePackage.getEString(), "activityName", null, 0, 1, WorkItem.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getWorkItem_Start(), this.getDate(), "start", null, 0, 1, WorkItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getWorkItem_EndDate(), this.getDate(), "endDate", null, 0, 1, WorkItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getWorkItem_End(), this.getDate(), "end", null, 0, 1, WorkItem.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getWorkItem_Duration(), this.getTimeSpan(), null, "duration", null, 0, 1, WorkItem.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getWorkItem_Description(), theEcorePackage.getEString(), "description", null, 0, 1, WorkItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    EOperation op = initEOperation(getWorkItem__FormatStart__DateFormat(), theEcorePackage.getEString(), "formatStart", 0, 1, !IS_UNIQUE, IS_ORDERED);
    addEParameter(op, this.getDateFormat(), "df", 0, 1, !IS_UNIQUE, IS_ORDERED);

    op = initEOperation(getWorkItem__FormatEnd__DateFormat(), theEcorePackage.getEString(), "formatEnd", 0, 1, !IS_UNIQUE, IS_ORDERED);
    addEParameter(op, this.getDateFormat(), "df", 0, 1, !IS_UNIQUE, IS_ORDERED);

    op = initEOperation(getWorkItem__CompareTo__WorkItem(), theEcorePackage.getEInt(), "compareTo", 0, 1, !IS_UNIQUE, IS_ORDERED);
    addEParameter(op, this.getWorkItem(), "wi", 0, 1, !IS_UNIQUE, IS_ORDERED);

    initEClass(workItemSummaryEClass, WorkItemSummary.class, "WorkItemSummary", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getWorkItemSummary_WorkItems(), this.getWorkItem(), null, "workItems", null, 0, -1, WorkItemSummary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getWorkItemSummary_ActivityName(), theEcorePackage.getEString(), "activityName", null, 0, 1, WorkItemSummary.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getWorkItemSummary_SumOfDurations(), this.getTimeSpan(), null, "sumOfDurations", null, 0, 1, WorkItemSummary.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getWorkItemSummary_SumOfDescriptions(), theEcorePackage.getEString(), "sumOfDescriptions", null, 0, -1, WorkItemSummary.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);

    initEClass(timeSpanEClass, TimeSpan.class, "TimeSpan", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getTimeSpan_Millis(), theEcorePackage.getELong(), "millis", null, 0, 1, TimeSpan.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getTimeSpan_Seconds(), theEcorePackage.getEInt(), "seconds", null, 0, 1, TimeSpan.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getTimeSpan_Minutes(), theEcorePackage.getEInt(), "minutes", null, 0, 1, TimeSpan.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getTimeSpan_Hours(), theEcorePackage.getEInt(), "hours", null, 0, 1, TimeSpan.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getTimeSpan_Days(), theEcorePackage.getEInt(), "days", null, 0, 1, TimeSpan.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getTimeSpan_Weeks(), theEcorePackage.getEInt(), "weeks", null, 0, 1, TimeSpan.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);

    initEOperation(getTimeSpan__InSeconds(), theEcorePackage.getELong(), "inSeconds", 0, 1, !IS_UNIQUE, IS_ORDERED);

    initEOperation(getTimeSpan__InMinutes(), theEcorePackage.getELong(), "inMinutes", 0, 1, !IS_UNIQUE, IS_ORDERED);

    initEOperation(getTimeSpan__InHours(), theEcorePackage.getELong(), "inHours", 0, 1, !IS_UNIQUE, IS_ORDERED);

    initEOperation(getTimeSpan__InDays(), theEcorePackage.getELong(), "inDays", 0, 1, !IS_UNIQUE, IS_ORDERED);

    initEOperation(getTimeSpan__InWeeks(), theEcorePackage.getELong(), "inWeeks", 0, 1, !IS_UNIQUE, IS_ORDERED);

    initEOperation(getTimeSpan__ToString(), theEcorePackage.getEString(), "toString", 0, 1, !IS_UNIQUE, IS_ORDERED);

    // Initialize data types
    initEDataType(dateEDataType, Date.class, "Date", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
    initEDataType(dateFormatEDataType, DateFormat.class, "DateFormat", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

    // Create resource
    createResource(eNS_URI);

    // Create annotations
    // http://www.eclipse.org/emf/2011/Xcore
    createXcoreAnnotations();
  }

  /**
   * Initializes the annotations for <b>http://www.eclipse.org/emf/2011/Xcore</b>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected void createXcoreAnnotations()
  {
    String source = "http://www.eclipse.org/emf/2011/Xcore";			
    addAnnotation
      (this, 
       source, 
       new String[] 
       {
       "Ecore", "http://www.eclipse.org/emf/2002/Ecore",
       "GenModel", "http://www.eclipse.org/emf/2002/GenModel"
       });																					
  }

} //DomainPackageImpl
