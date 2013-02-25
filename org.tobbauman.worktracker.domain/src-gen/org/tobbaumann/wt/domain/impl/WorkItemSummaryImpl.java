/**
 */
package org.tobbaumann.wt.domain.impl;

import com.google.common.base.Strings;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;

import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

import org.tobbaumann.wt.domain.DomainFactory;
import org.tobbaumann.wt.domain.DomainPackage;
import org.tobbaumann.wt.domain.TimeSpan;
import org.tobbaumann.wt.domain.WorkItem;
import org.tobbaumann.wt.domain.WorkItemSummary;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Work Item Summary</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.tobbaumann.wt.domain.impl.WorkItemSummaryImpl#getWorkItems <em>Work Items</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.impl.WorkItemSummaryImpl#getActivityName <em>Activity Name</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.impl.WorkItemSummaryImpl#getSumOfDurations <em>Sum Of Durations</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.impl.WorkItemSummaryImpl#getSumOfDescriptions <em>Sum Of Descriptions</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkItemSummaryImpl extends MinimalEObjectImpl.Container implements WorkItemSummary
{
  /**
   * The cached value of the '{@link #getWorkItems() <em>Work Items</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWorkItems()
   * @generated
   * @ordered
   */
  protected EList<WorkItem> workItems;

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
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected WorkItemSummaryImpl()
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
    return DomainPackage.Literals.WORK_ITEM_SUMMARY;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<WorkItem> getWorkItems()
  {
    if (workItems == null)
    {
      workItems = new EObjectResolvingEList<WorkItem>(WorkItem.class, this, DomainPackage.WORK_ITEM_SUMMARY__WORK_ITEMS);
    }
    return workItems;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getActivityName()
  {
    EList<WorkItem> _workItems = this.getWorkItems();
    boolean _isEmpty = _workItems.isEmpty();
    if (_isEmpty)
    {
      return null;
    }
    EList<WorkItem> _workItems_1 = this.getWorkItems();
    WorkItem _get = _workItems_1.get(0);
    return _get.getActivityName();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TimeSpan getSumOfDurations()
  {
    final TimeSpan ts = DomainFactory.eINSTANCE.createTimeSpan();
    EList<WorkItem> _workItems = this.getWorkItems();
    final Function1<WorkItem,Long> _function = new Function1<WorkItem,Long>()
    {
        public Long apply(final WorkItem it)
        {
          TimeSpan _duration = it.getDuration();
          long _millis = _duration.getMillis();
          return Long.valueOf(_millis);
        }
      };
    List<Long> _map = ListExtensions.<WorkItem, Long>map(_workItems, _function);
    final Function2<Long,Long,Long> _function_1 = new Function2<Long,Long,Long>()
    {
        public Long apply(final Long a, final Long b)
        {
          long _plus = ((a).longValue() + (b).longValue());
          return Long.valueOf(_plus);
        }
      };
    Long _reduce = IterableExtensions.<Long>reduce(_map, _function_1);
    ts.setMillis((_reduce).longValue());
    return ts;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetSumOfDurations(TimeSpan newSumOfDurations, NotificationChain msgs)
  {
    // TODO: implement this method to set the contained 'Sum Of Durations' containment reference
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
  public EList<String> getSumOfDescriptions()
  {
    EList<WorkItem> _workItems = this.getWorkItems();
    final Function1<WorkItem,String> _function = new Function1<WorkItem,String>()
    {
        public String apply(final WorkItem it)
        {
          String _description = it.getDescription();
          String _nullToEmpty = Strings.nullToEmpty(_description);
          return _nullToEmpty;
        }
      };
    List<String> _map = ListExtensions.<WorkItem, String>map(_workItems, _function);
    BasicEList<String> _basicEList = new BasicEList<String>(_map);
    return _basicEList;
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
      case DomainPackage.WORK_ITEM_SUMMARY__SUM_OF_DURATIONS:
        return basicSetSumOfDurations(null, msgs);
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
      case DomainPackage.WORK_ITEM_SUMMARY__WORK_ITEMS:
        return getWorkItems();
      case DomainPackage.WORK_ITEM_SUMMARY__ACTIVITY_NAME:
        return getActivityName();
      case DomainPackage.WORK_ITEM_SUMMARY__SUM_OF_DURATIONS:
        return getSumOfDurations();
      case DomainPackage.WORK_ITEM_SUMMARY__SUM_OF_DESCRIPTIONS:
        return getSumOfDescriptions();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case DomainPackage.WORK_ITEM_SUMMARY__WORK_ITEMS:
        getWorkItems().clear();
        getWorkItems().addAll((Collection<? extends WorkItem>)newValue);
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
      case DomainPackage.WORK_ITEM_SUMMARY__WORK_ITEMS:
        getWorkItems().clear();
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
      case DomainPackage.WORK_ITEM_SUMMARY__WORK_ITEMS:
        return workItems != null && !workItems.isEmpty();
      case DomainPackage.WORK_ITEM_SUMMARY__ACTIVITY_NAME:
        return ACTIVITY_NAME_EDEFAULT == null ? getActivityName() != null : !ACTIVITY_NAME_EDEFAULT.equals(getActivityName());
      case DomainPackage.WORK_ITEM_SUMMARY__SUM_OF_DURATIONS:
        return getSumOfDurations() != null;
      case DomainPackage.WORK_ITEM_SUMMARY__SUM_OF_DESCRIPTIONS:
        return !getSumOfDescriptions().isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //WorkItemSummaryImpl
