/**
 */
package org.tobbaumann.wt.domain.impl;

import java.lang.reflect.InvocationTargetException;

import java.math.BigDecimal;

import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.NotificationChain;

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
import org.tobbaumann.wt.domain.WorkItemSummaries;
import org.tobbaumann.wt.domain.WorkItemSummary;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Work Item Summaries</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.tobbaumann.wt.domain.impl.WorkItemSummariesImpl#getWorkItemSummaries <em>Work Item Summaries</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.impl.WorkItemSummariesImpl#getSumOfDurations <em>Sum Of Durations</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkItemSummariesImpl extends MinimalEObjectImpl.Container implements WorkItemSummaries
{
  /**
   * The cached value of the '{@link #getWorkItemSummaries() <em>Work Item Summaries</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWorkItemSummaries()
   * @generated
   * @ordered
   */
  protected EList<WorkItemSummary> workItemSummaries;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected WorkItemSummariesImpl()
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
    return DomainPackage.Literals.WORK_ITEM_SUMMARIES;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<WorkItemSummary> getWorkItemSummaries()
  {
    if (workItemSummaries == null)
    {
      workItemSummaries = new EObjectResolvingEList<WorkItemSummary>(WorkItemSummary.class, this, DomainPackage.WORK_ITEM_SUMMARIES__WORK_ITEM_SUMMARIES);
    }
    return workItemSummaries;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TimeSpan getSumOfDurations()
  {
    EList<WorkItemSummary> _workItemSummaries = this.getWorkItemSummaries();
    final Function1<WorkItemSummary,Long> _function = new Function1<WorkItemSummary,Long>()
    {
        public Long apply(final WorkItemSummary it)
        {
          TimeSpan _sumOfDurations = it.getSumOfDurations();
          long _millis = _sumOfDurations.getMillis();
          return Long.valueOf(_millis);
        }
      };
    List<Long> _map = ListExtensions.<WorkItemSummary, Long>map(_workItemSummaries, _function);
    final Function2<Long,Long,Long> _function_1 = new Function2<Long,Long,Long>()
    {
        public Long apply(final Long a, final Long b)
        {
          long _plus = ((a).longValue() + (b).longValue());
          return Long.valueOf(_plus);
        }
      };
    final long duration = (IterableExtensions.<Long>reduce(_map, _function_1)).longValue();
    final TimeSpan ts = DomainFactory.eINSTANCE.createTimeSpan();
    ts.setMillis(duration);
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
  public BigDecimal computeDurationRatioInPercent(WorkItemSummary wis)
  {
    WorkItemSummaries _this = this;
    final TimeSpan durationAll = _this.getSumOfDurations();
    long _millis = durationAll.getMillis();
    boolean _equals = (_millis == 0);
    if (_equals)
    {
      return BigDecimal.ZERO;
    }
    TimeSpan _sumOfDurations = wis.getSumOfDurations();
    long _millis_1 = _sumOfDurations.getMillis();
    BigDecimal _bigDecimal = new BigDecimal(_millis_1);
    final BigDecimal durationSingle = _bigDecimal;
    long _millis_2 = durationAll.getMillis();
    BigDecimal _bigDecimal_1 = new BigDecimal(_millis_2);
    final BigDecimal durationAllMillis = _bigDecimal_1;
    BigDecimal _divide = durationSingle.divide(durationAllMillis, 10, RoundingMode.HALF_UP);
    BigDecimal _bigDecimal_2 = new BigDecimal(100);
    final BigDecimal ratio = _divide.multiply(_bigDecimal_2);
    return ratio;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TimeSpan computeDurationRatio(WorkItemSummary wis, int totalMinutes)
  {
    WorkItemSummaries _this = this;
    final BigDecimal ratio = _this.computeDurationRatioInPercent(wis);
    BigDecimal _bigDecimal = new BigDecimal(totalMinutes);
    int _multiply = (60 * 1000);
    BigDecimal _bigDecimal_1 = new BigDecimal(_multiply);
    final BigDecimal totalMillis = _bigDecimal.multiply(_bigDecimal_1);
    BigDecimal _bigDecimal_2 = new BigDecimal(100);
    BigDecimal _divide = ratio.divide(_bigDecimal_2, 10, RoundingMode.HALF_UP);
    final BigDecimal ratioMillis = _divide.multiply(totalMillis);
    final TimeSpan ts = DomainFactory.eINSTANCE.createTimeSpan();
    long _longValue = ratioMillis.longValue();
    ts.setMillis(_longValue);
    return ts;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TimeSpan computeSumOfDurationRatio(int totalMinutes)
  {
    long millis = 0;
    WorkItemSummaries _this = this;
    EList<WorkItemSummary> _workItemSummaries = _this.getWorkItemSummaries();
    for (final WorkItemSummary wis : _workItemSummaries)
    {
      WorkItemSummaries _this_1 = this;
      TimeSpan _computeDurationRatio = _this_1.computeDurationRatio(wis, totalMinutes);
      long _millis = _computeDurationRatio.getMillis();
      long _plus = (millis + _millis);
      millis = _plus;
    }
    final TimeSpan ts = DomainFactory.eINSTANCE.createTimeSpan();
    ts.setMillis(millis);
    return ts;
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
      case DomainPackage.WORK_ITEM_SUMMARIES__SUM_OF_DURATIONS:
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
      case DomainPackage.WORK_ITEM_SUMMARIES__WORK_ITEM_SUMMARIES:
        return getWorkItemSummaries();
      case DomainPackage.WORK_ITEM_SUMMARIES__SUM_OF_DURATIONS:
        return getSumOfDurations();
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
      case DomainPackage.WORK_ITEM_SUMMARIES__WORK_ITEM_SUMMARIES:
        getWorkItemSummaries().clear();
        getWorkItemSummaries().addAll((Collection<? extends WorkItemSummary>)newValue);
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
      case DomainPackage.WORK_ITEM_SUMMARIES__WORK_ITEM_SUMMARIES:
        getWorkItemSummaries().clear();
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
      case DomainPackage.WORK_ITEM_SUMMARIES__WORK_ITEM_SUMMARIES:
        return workItemSummaries != null && !workItemSummaries.isEmpty();
      case DomainPackage.WORK_ITEM_SUMMARIES__SUM_OF_DURATIONS:
        return getSumOfDurations() != null;
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
      case DomainPackage.WORK_ITEM_SUMMARIES___COMPUTE_DURATION_RATIO_IN_PERCENT__WORKITEMSUMMARY:
        return computeDurationRatioInPercent((WorkItemSummary)arguments.get(0));
      case DomainPackage.WORK_ITEM_SUMMARIES___COMPUTE_DURATION_RATIO__WORKITEMSUMMARY_INT:
        return computeDurationRatio((WorkItemSummary)arguments.get(0), (Integer)arguments.get(1));
      case DomainPackage.WORK_ITEM_SUMMARIES___COMPUTE_SUM_OF_DURATION_RATIO__INT:
        return computeSumOfDurationRatio((Integer)arguments.get(0));
    }
    return super.eInvoke(operationID, arguments);
  }

} //WorkItemSummariesImpl
