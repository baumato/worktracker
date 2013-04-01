/**
 */
package org.tobbaumann.wt.domain.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.tobbaumann.wt.domain.Activity;
import org.tobbaumann.wt.domain.DomainPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Activity</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.tobbaumann.wt.domain.impl.ActivityImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.impl.ActivityImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.impl.ActivityImpl#isInUse <em>In Use</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.impl.ActivityImpl#getOccurrenceFrequency <em>Occurrence Frequency</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ActivityImpl extends MinimalEObjectImpl.Container implements Activity
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
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * The default value of the '{@link #isInUse() <em>In Use</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isInUse()
   * @generated
   * @ordered
   */
  protected static final boolean IN_USE_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isInUse() <em>In Use</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isInUse()
   * @generated
   * @ordered
   */
  protected boolean inUse = IN_USE_EDEFAULT;

  /**
   * The default value of the '{@link #getOccurrenceFrequency() <em>Occurrence Frequency</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOccurrenceFrequency()
   * @generated
   * @ordered
   */
  protected static final long OCCURRENCE_FREQUENCY_EDEFAULT = 0L;

  /**
   * The cached value of the '{@link #getOccurrenceFrequency() <em>Occurrence Frequency</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOccurrenceFrequency()
   * @generated
   * @ordered
   */
  protected long occurrenceFrequency = OCCURRENCE_FREQUENCY_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ActivityImpl()
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
    return DomainPackage.Literals.ACTIVITY;
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
      eNotify(new ENotificationImpl(this, Notification.SET, DomainPackage.ACTIVITY__ID, oldId, id));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setName(String newName)
  {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, DomainPackage.ACTIVITY__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isInUse()
  {
    return inUse;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setInUse(boolean newInUse)
  {
    boolean oldInUse = inUse;
    inUse = newInUse;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, DomainPackage.ACTIVITY__IN_USE, oldInUse, inUse));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public long getOccurrenceFrequency()
  {
    return occurrenceFrequency;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setOccurrenceFrequency(long newOccurrenceFrequency)
  {
    long oldOccurrenceFrequency = occurrenceFrequency;
    occurrenceFrequency = newOccurrenceFrequency;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, DomainPackage.ACTIVITY__OCCURRENCE_FREQUENCY, oldOccurrenceFrequency, occurrenceFrequency));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void incrementOccurrenceFrequency()
  {
    Activity _this = this;
    Activity _this_1 = this;
    long _occurrenceFrequency = _this_1.getOccurrenceFrequency();
    long _plus = (_occurrenceFrequency + 1);
    _this.setOccurrenceFrequency(_plus);
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
      case DomainPackage.ACTIVITY__ID:
        return getId();
      case DomainPackage.ACTIVITY__NAME:
        return getName();
      case DomainPackage.ACTIVITY__IN_USE:
        return isInUse();
      case DomainPackage.ACTIVITY__OCCURRENCE_FREQUENCY:
        return getOccurrenceFrequency();
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
      case DomainPackage.ACTIVITY__ID:
        setId((String)newValue);
        return;
      case DomainPackage.ACTIVITY__NAME:
        setName((String)newValue);
        return;
      case DomainPackage.ACTIVITY__IN_USE:
        setInUse((Boolean)newValue);
        return;
      case DomainPackage.ACTIVITY__OCCURRENCE_FREQUENCY:
        setOccurrenceFrequency((Long)newValue);
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
      case DomainPackage.ACTIVITY__ID:
        setId(ID_EDEFAULT);
        return;
      case DomainPackage.ACTIVITY__NAME:
        setName(NAME_EDEFAULT);
        return;
      case DomainPackage.ACTIVITY__IN_USE:
        setInUse(IN_USE_EDEFAULT);
        return;
      case DomainPackage.ACTIVITY__OCCURRENCE_FREQUENCY:
        setOccurrenceFrequency(OCCURRENCE_FREQUENCY_EDEFAULT);
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
      case DomainPackage.ACTIVITY__ID:
        return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
      case DomainPackage.ACTIVITY__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case DomainPackage.ACTIVITY__IN_USE:
        return inUse != IN_USE_EDEFAULT;
      case DomainPackage.ACTIVITY__OCCURRENCE_FREQUENCY:
        return occurrenceFrequency != OCCURRENCE_FREQUENCY_EDEFAULT;
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
      case DomainPackage.ACTIVITY___INCREMENT_OCCURRENCE_FREQUENCY:
        incrementOccurrenceFrequency();
        return null;
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
    result.append(", name: ");
    result.append(name);
    result.append(", inUse: ");
    result.append(inUse);
    result.append(", occurrenceFrequency: ");
    result.append(occurrenceFrequency);
    result.append(')');
    return result.toString();
  }

} //ActivityImpl
