/**
 */
package org.tobbaumann.wt.domain.impl;

import java.lang.Iterable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.tobbaumann.wt.domain.Activities;
import org.tobbaumann.wt.domain.Activity;
import org.tobbaumann.wt.domain.DomainPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Activities</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.tobbaumann.wt.domain.impl.ActivitiesImpl#getActivities <em>Activities</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ActivitiesImpl extends MinimalEObjectImpl.Container implements Activities
{
	/**
   * The cached value of the '{@link #getActivities() <em>Activities</em>}' containment reference list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getActivities()
   * @generated
   * @ordered
   */
	protected EList<Activity> activities;

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected ActivitiesImpl()
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
    return DomainPackage.Literals.ACTIVITIES;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	public EList<Activity> getActivities()
	{
    if (activities == null)
    {
      activities = new EObjectContainmentEList<Activity>(Activity.class, this, DomainPackage.ACTIVITIES__ACTIVITIES);
    }
    return activities;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	public EList<Activity> getMostUsedActivities(int numberOfActivities)
	{
    Activities _this = this;
    EList<Activity> _activities = _this.getActivities();
    final Function1<Activity,Long> _function = new Function1<Activity,Long>()
    {
        public Long apply(final Activity it)
        {
          long _occurrenceFrequency = it.getOccurrenceFrequency();
          return Long.valueOf(_occurrenceFrequency);
        }
      };
    List<Activity> _sortBy = IterableExtensions.<Activity, Long>sortBy(_activities, _function);
    Iterable<Activity> _take = IterableExtensions.<Activity>take(_sortBy, numberOfActivities);
    List<Activity> _list = IterableExtensions.<Activity>toList(_take);
    BasicEList<Activity> _basicEList = new BasicEList<Activity>(_list);
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
      case DomainPackage.ACTIVITIES__ACTIVITIES:
        return ((InternalEList<?>)getActivities()).basicRemove(otherEnd, msgs);
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
      case DomainPackage.ACTIVITIES__ACTIVITIES:
        return getActivities();
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
      case DomainPackage.ACTIVITIES__ACTIVITIES:
        getActivities().clear();
        getActivities().addAll((Collection<? extends Activity>)newValue);
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
      case DomainPackage.ACTIVITIES__ACTIVITIES:
        getActivities().clear();
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
      case DomainPackage.ACTIVITIES__ACTIVITIES:
        return activities != null && !activities.isEmpty();
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
      case DomainPackage.ACTIVITIES___GET_MOST_USED_ACTIVITIES__INT:
        return getMostUsedActivities((Integer)arguments.get(0));
    }
    return super.eInvoke(operationID, arguments);
  }

} //ActivitiesImpl
