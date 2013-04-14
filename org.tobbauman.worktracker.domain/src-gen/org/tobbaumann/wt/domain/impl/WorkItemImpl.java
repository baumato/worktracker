/**
 */
package org.tobbaumann.wt.domain.impl;

import com.google.common.base.Objects;

import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.xtext.xbase.lib.Exceptions;

import org.tobbaumann.wt.domain.Activity;
import org.tobbaumann.wt.domain.DomainFactory;
import org.tobbaumann.wt.domain.DomainPackage;
import org.tobbaumann.wt.domain.TimeSpan;
import org.tobbaumann.wt.domain.WorkItem;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Work Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.tobbaumann.wt.domain.impl.WorkItemImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.impl.WorkItemImpl#getActivity <em>Activity</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.impl.WorkItemImpl#getActivityName <em>Activity Name</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.impl.WorkItemImpl#getStart <em>Start</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.impl.WorkItemImpl#getEndDate <em>End Date</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.impl.WorkItemImpl#getEnd <em>End</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.impl.WorkItemImpl#getDuration <em>Duration</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.impl.WorkItemImpl#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkItemImpl extends MinimalEObjectImpl.Container implements WorkItem
{
  /**
   * The default value of the '{@link #getId() <em>Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getId()
   * @generated
   * @ordered
   */
  protected static final String ID_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getId()
   * @generated
   * @ordered
   */
  protected String id = ID_EDEFAULT;

  /**
   * The cached value of the '{@link #getActivity() <em>Activity</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getActivity()
   * @generated
   * @ordered
   */
  protected Activity activity;

  /**
   * The default value of the '{@link #getActivityName() <em>Activity Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getActivityName()
   * @generated
   * @ordered
   */
  protected static final String ACTIVITY_NAME_EDEFAULT = null;

  /**
   * The default value of the '{@link #getStart() <em>Start</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStart()
   * @generated
   * @ordered
   */
  protected static final Date START_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getStart() <em>Start</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStart()
   * @generated
   * @ordered
   */
  protected Date start = START_EDEFAULT;

  /**
   * The default value of the '{@link #getEndDate() <em>End Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEndDate()
   * @generated
   * @ordered
   */
  protected static final Date END_DATE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getEndDate() <em>End Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEndDate()
   * @generated
   * @ordered
   */
  protected Date endDate = END_DATE_EDEFAULT;

  /**
   * The default value of the '{@link #getEnd() <em>End</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEnd()
   * @generated
   * @ordered
   */
  protected static final Date END_EDEFAULT = null;

  /**
   * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDescription()
   * @generated
   * @ordered
   */
  protected static final String DESCRIPTION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDescription()
   * @generated
   * @ordered
   */
  protected String description = DESCRIPTION_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected WorkItemImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return DomainPackage.Literals.WORK_ITEM;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getId()
  {
    return id;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setId(String newId)
  {
    String oldId = id;
    id = newId;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, DomainPackage.WORK_ITEM__ID, oldId, id));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Activity getActivity()
  {
    if (activity != null && activity.eIsProxy())
    {
      InternalEObject oldActivity = (InternalEObject)activity;
      activity = (Activity)eResolveProxy(oldActivity);
      if (activity != oldActivity)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, DomainPackage.WORK_ITEM__ACTIVITY, oldActivity, activity));
      }
    }
    return activity;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Activity basicGetActivity()
  {
    return activity;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setActivity(Activity newActivity)
  {
    Activity oldActivity = activity;
    activity = newActivity;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, DomainPackage.WORK_ITEM__ACTIVITY, oldActivity, activity));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getActivityName()
  {
    String _xifexpression = null;
    Activity _activity = this.getActivity();
    boolean _notEquals = (!Objects.equal(_activity, null));
    if (_notEquals)
    {
      Activity _activity_1 = this.getActivity();
      String _name = _activity_1.getName();
      _xifexpression = _name;
    }
    else
    {
      _xifexpression = null;
    }
    return _xifexpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Date getStart()
  {
    return start;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setStart(Date newStart)
  {
    Date oldStart = start;
    start = newStart;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, DomainPackage.WORK_ITEM__START, oldStart, start));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Date getEndDate()
  {
    return endDate;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setEndDate(Date newEndDate)
  {
    Date oldEndDate = endDate;
    endDate = newEndDate;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, DomainPackage.WORK_ITEM__END_DATE, oldEndDate, endDate));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Date getEnd()
  {
    Date _xifexpression = null;
    Date _endDate = this.getEndDate();
    boolean _equals = Objects.equal(_endDate, null);
    if (_equals)
    {
      Date _date = new Date();
      _xifexpression = _date;
    }
    else
    {
      Date _endDate_1 = this.getEndDate();
      _xifexpression = _endDate_1;
    }
    return _xifexpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TimeSpan getDuration()
  {
    final TimeSpan ts = DomainFactory.eINSTANCE.createTimeSpan();
    Date _xifexpression = null;
    Date _start = this.getStart();
    boolean _equals = Objects.equal(_start, null);
    if (_equals)
    {
      Date _date = new Date();
      _xifexpression = _date;
    }
    else
    {
      Date _start_1 = this.getStart();
      _xifexpression = _start_1;
    }
    final Date s = _xifexpression;
    Date _xifexpression_1 = null;
    Date _end = this.getEnd();
    boolean _equals_1 = Objects.equal(_end, null);
    if (_equals_1)
    {
      _xifexpression_1 = s;
    }
    else
    {
      Date _end_1 = this.getEnd();
      _xifexpression_1 = _end_1;
    }
    final Date e = _xifexpression_1;
    final TimeSpanHelper h = TimeSpanHelper.getInstance(s, e);
    int _inSeconds = h.inSeconds();
    int _multiply = (_inSeconds * 1000);
    ts.setMillis(_multiply);
    return ts;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetDuration(TimeSpan newDuration, NotificationChain msgs)
  {
    // TODO: implement this method to set the contained 'Duration' containment reference
    // -> this method is automatically invoked to keep the containment relationship in synch
    // -> do not modify other features
    // -> return msgs, after adding any generated Notification to it (if it is null, a NotificationChain object must be created first)
    // Ensure that you remove @generated or mark it @generated NOT
    throw new UnsupportedOperationException();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getDescription()
  {
    return description;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDescription(String newDescription)
  {
    String oldDescription = description;
    description = newDescription;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, DomainPackage.WORK_ITEM__DESCRIPTION, oldDescription, description));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String formatStart(DateFormat df)
  {
    WorkItem _this = this;
    Date _start = _this.getStart();
    return df.format(_start);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Date getDatePartOfStart()
  {
    try
    {
      final DateFormat df = DateFormat.getDateInstance();
      WorkItem _this = this;
      String _formatStart = _this.formatStart(df);
      return df.parse(_formatStart);
    }
    catch (final Throwable _t) {
      if (_t instanceof ParseException) {
        final ParseException e = (ParseException)_t;
        RuntimeException _runtimeException = new RuntimeException(e);
        throw _runtimeException;
      }
      else
      {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String formatEnd(DateFormat df)
  {
    WorkItem _this = this;
    Date _end = _this.getEnd();
    return df.format(_end);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int compareTo(WorkItem wi)
  {
    WorkItem _this = this;
    Date _start = _this.getStart();
    Date _start_1 = wi.getStart();
    return _start.compareTo(_start_1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case DomainPackage.WORK_ITEM__DURATION:
        return basicSetDuration(null, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case DomainPackage.WORK_ITEM__ID:
        return getId();
      case DomainPackage.WORK_ITEM__ACTIVITY:
        if (resolve) return getActivity();
        return basicGetActivity();
      case DomainPackage.WORK_ITEM__ACTIVITY_NAME:
        return getActivityName();
      case DomainPackage.WORK_ITEM__START:
        return getStart();
      case DomainPackage.WORK_ITEM__END_DATE:
        return getEndDate();
      case DomainPackage.WORK_ITEM__END:
        return getEnd();
      case DomainPackage.WORK_ITEM__DURATION:
        return getDuration();
      case DomainPackage.WORK_ITEM__DESCRIPTION:
        return getDescription();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case DomainPackage.WORK_ITEM__ID:
        setId((String)newValue);
        return;
      case DomainPackage.WORK_ITEM__ACTIVITY:
        setActivity((Activity)newValue);
        return;
      case DomainPackage.WORK_ITEM__START:
        setStart((Date)newValue);
        return;
      case DomainPackage.WORK_ITEM__END_DATE:
        setEndDate((Date)newValue);
        return;
      case DomainPackage.WORK_ITEM__DESCRIPTION:
        setDescription((String)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case DomainPackage.WORK_ITEM__ID:
        setId(ID_EDEFAULT);
        return;
      case DomainPackage.WORK_ITEM__ACTIVITY:
        setActivity((Activity)null);
        return;
      case DomainPackage.WORK_ITEM__START:
        setStart(START_EDEFAULT);
        return;
      case DomainPackage.WORK_ITEM__END_DATE:
        setEndDate(END_DATE_EDEFAULT);
        return;
      case DomainPackage.WORK_ITEM__DESCRIPTION:
        setDescription(DESCRIPTION_EDEFAULT);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case DomainPackage.WORK_ITEM__ID:
        return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
      case DomainPackage.WORK_ITEM__ACTIVITY:
        return activity != null;
      case DomainPackage.WORK_ITEM__ACTIVITY_NAME:
        return ACTIVITY_NAME_EDEFAULT == null ? getActivityName() != null : !ACTIVITY_NAME_EDEFAULT.equals(getActivityName());
      case DomainPackage.WORK_ITEM__START:
        return START_EDEFAULT == null ? start != null : !START_EDEFAULT.equals(start);
      case DomainPackage.WORK_ITEM__END_DATE:
        return END_DATE_EDEFAULT == null ? endDate != null : !END_DATE_EDEFAULT.equals(endDate);
      case DomainPackage.WORK_ITEM__END:
        return END_EDEFAULT == null ? getEnd() != null : !END_EDEFAULT.equals(getEnd());
      case DomainPackage.WORK_ITEM__DURATION:
        return getDuration() != null;
      case DomainPackage.WORK_ITEM__DESCRIPTION:
        return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException
  {
    switch (operationID)
    {
      case DomainPackage.WORK_ITEM___FORMAT_START__DATEFORMAT:
        return formatStart((DateFormat)arguments.get(0));
      case DomainPackage.WORK_ITEM___GET_DATE_PART_OF_START:
        return getDatePartOfStart();
      case DomainPackage.WORK_ITEM___FORMAT_END__DATEFORMAT:
        return formatEnd((DateFormat)arguments.get(0));
      case DomainPackage.WORK_ITEM___COMPARE_TO__WORKITEM:
        return compareTo((WorkItem)arguments.get(0));
    }
    return super.eInvoke(operationID, arguments);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (id: ");
    result.append(id);
    result.append(", start: ");
    result.append(start);
    result.append(", endDate: ");
    result.append(endDate);
    result.append(", description: ");
    result.append(description);
    result.append(')');
    return result.toString();
  }

} //WorkItemImpl
