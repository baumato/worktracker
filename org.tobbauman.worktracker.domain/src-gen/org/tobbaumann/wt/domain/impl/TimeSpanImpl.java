/**
 */
package org.tobbaumann.wt.domain.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.tobbaumann.wt.domain.DomainPackage;
import org.tobbaumann.wt.domain.TimeSpan;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Time Span</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.tobbaumann.wt.domain.impl.TimeSpanImpl#getMillis <em>Millis</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.impl.TimeSpanImpl#getSeconds <em>Seconds</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.impl.TimeSpanImpl#getMinutes <em>Minutes</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.impl.TimeSpanImpl#getHours <em>Hours</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.impl.TimeSpanImpl#getDays <em>Days</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.impl.TimeSpanImpl#getWeeks <em>Weeks</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TimeSpanImpl extends MinimalEObjectImpl.Container implements TimeSpan
{
  /**
   * The default value of the '{@link #getMillis() <em>Millis</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMillis()
   * @generated
   * @ordered
   */
  protected static final long MILLIS_EDEFAULT = 0L;

  /**
   * The cached value of the '{@link #getMillis() <em>Millis</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMillis()
   * @generated
   * @ordered
   */
  protected long millis = MILLIS_EDEFAULT;

  /**
   * The default value of the '{@link #getSeconds() <em>Seconds</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSeconds()
   * @generated
   * @ordered
   */
  protected static final int SECONDS_EDEFAULT = 0;

  /**
   * The default value of the '{@link #getMinutes() <em>Minutes</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMinutes()
   * @generated
   * @ordered
   */
  protected static final int MINUTES_EDEFAULT = 0;

  /**
   * The default value of the '{@link #getHours() <em>Hours</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHours()
   * @generated
   * @ordered
   */
  protected static final int HOURS_EDEFAULT = 0;

  /**
   * The default value of the '{@link #getDays() <em>Days</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDays()
   * @generated
   * @ordered
   */
  protected static final int DAYS_EDEFAULT = 0;

  /**
   * The default value of the '{@link #getWeeks() <em>Weeks</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWeeks()
   * @generated
   * @ordered
   */
  protected static final int WEEKS_EDEFAULT = 0;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TimeSpanImpl()
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
    return DomainPackage.Literals.TIME_SPAN;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public long getMillis()
  {
    return millis;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMillis(long newMillis)
  {
    long oldMillis = millis;
    millis = newMillis;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, DomainPackage.TIME_SPAN__MILLIS, oldMillis, millis));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getSeconds()
  {
    long _millis = this.getMillis();
    long _divide = (_millis / 1000);
    long _modulo = (_divide % 60);
    return Long.valueOf(_modulo).intValue();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getMinutes()
  {
    long _millis = this.getMillis();
    long _divide = (_millis / 1000);
    long _divide_1 = (_divide / 60);
    long _modulo = (_divide_1 % 60);
    return Long.valueOf(_modulo).intValue();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getHours()
  {
    long _millis = this.getMillis();
    long _divide = (_millis / 1000);
    long _divide_1 = (_divide / 60);
    long _divide_2 = (_divide_1 / 60);
    long _modulo = (_divide_2 % 24);
    return Long.valueOf(_modulo).intValue();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getDays()
  {
    long _millis = this.getMillis();
    long _divide = (_millis / 1000);
    long _divide_1 = (_divide / 60);
    long _divide_2 = (_divide_1 / 60);
    long _divide_3 = (_divide_2 / 24);
    long _modulo = (_divide_3 % 7);
    return Long.valueOf(_modulo).intValue();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getWeeks()
  {
    long _millis = this.getMillis();
    long _divide = (_millis / 1000);
    long _divide_1 = (_divide / 60);
    long _divide_2 = (_divide_1 / 60);
    long _divide_3 = (_divide_2 / 24);
    long _divide_4 = (_divide_3 / 7);
    return Long.valueOf(_divide_4).intValue();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public long inSeconds()
  {
    TimeSpan _this = this;
    long _millis = _this.getMillis();
    return (_millis / 1000);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public long inMinutes()
  {
    TimeSpan _this = this;
    int _seconds = _this.getSeconds();
    return (_seconds / 60);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public long inHours()
  {
    TimeSpan _this = this;
    long _inMinutes = _this.inMinutes();
    return (_inMinutes / 60);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public long inDays()
  {
    TimeSpan _this = this;
    long _inHours = _this.inHours();
    return (_inHours / 24);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public long inWeeks()
  {
    TimeSpan _this = this;
    long _inDays = _this.inDays();
    return (_inDays / 7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String asString()
  {
    StringBuilder _stringBuilder = new StringBuilder();
    final StringBuilder s = _stringBuilder;
    TimeSpan _this = this;
    int _weeks = _this.getWeeks();
    boolean _greaterThan = (_weeks > 0);
    if (_greaterThan)
    {
      TimeSpan _this_1 = this;
      int _weeks_1 = _this_1.getWeeks();
      String _plus = (Integer.valueOf(_weeks_1) + " w  ");
      s.append(_plus);
    }
    TimeSpan _this_2 = this;
    int _days = _this_2.getDays();
    boolean _greaterThan_1 = (_days > 0);
    if (_greaterThan_1)
    {
      TimeSpan _this_3 = this;
      int _days_1 = _this_3.getDays();
      String _plus_1 = (Integer.valueOf(_days_1) + " d  ");
      s.append(_plus_1);
    }
    TimeSpan _this_4 = this;
    int _hours = _this_4.getHours();
    boolean _greaterThan_2 = (_hours > 0);
    if (_greaterThan_2)
    {
      TimeSpan _this_5 = this;
      int _hours_1 = _this_5.getHours();
      String _plus_2 = (Integer.valueOf(_hours_1) + " h  ");
      s.append(_plus_2);
    }
    TimeSpan _this_6 = this;
    int _minutes = _this_6.getMinutes();
    boolean _greaterThan_3 = (_minutes > 0);
    if (_greaterThan_3)
    {
      TimeSpan _this_7 = this;
      int _minutes_1 = _this_7.getMinutes();
      String _plus_3 = (Integer.valueOf(_minutes_1) + " m  ");
      s.append(_plus_3);
    }
    TimeSpan _this_8 = this;
    int _seconds = _this_8.getSeconds();
    boolean _greaterThan_4 = (_seconds > 0);
    if (_greaterThan_4)
    {
      TimeSpan _this_9 = this;
      int _seconds_1 = _this_9.getSeconds();
      String _plus_4 = (Integer.valueOf(_seconds_1) + " s  ");
      s.append(_plus_4);
    }
    String _string = s.toString();
    boolean _isEmpty = _string.isEmpty();
    if (_isEmpty)
    {
      s.append("0 s");
    }
    String _string_1 = s.toString();
    return _string_1.trim();
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
      case DomainPackage.TIME_SPAN__MILLIS:
        return getMillis();
      case DomainPackage.TIME_SPAN__SECONDS:
        return getSeconds();
      case DomainPackage.TIME_SPAN__MINUTES:
        return getMinutes();
      case DomainPackage.TIME_SPAN__HOURS:
        return getHours();
      case DomainPackage.TIME_SPAN__DAYS:
        return getDays();
      case DomainPackage.TIME_SPAN__WEEKS:
        return getWeeks();
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
      case DomainPackage.TIME_SPAN__MILLIS:
        setMillis((Long)newValue);
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
      case DomainPackage.TIME_SPAN__MILLIS:
        setMillis(MILLIS_EDEFAULT);
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
      case DomainPackage.TIME_SPAN__MILLIS:
        return millis != MILLIS_EDEFAULT;
      case DomainPackage.TIME_SPAN__SECONDS:
        return getSeconds() != SECONDS_EDEFAULT;
      case DomainPackage.TIME_SPAN__MINUTES:
        return getMinutes() != MINUTES_EDEFAULT;
      case DomainPackage.TIME_SPAN__HOURS:
        return getHours() != HOURS_EDEFAULT;
      case DomainPackage.TIME_SPAN__DAYS:
        return getDays() != DAYS_EDEFAULT;
      case DomainPackage.TIME_SPAN__WEEKS:
        return getWeeks() != WEEKS_EDEFAULT;
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
      case DomainPackage.TIME_SPAN___IN_SECONDS:
        return inSeconds();
      case DomainPackage.TIME_SPAN___IN_MINUTES:
        return inMinutes();
      case DomainPackage.TIME_SPAN___IN_HOURS:
        return inHours();
      case DomainPackage.TIME_SPAN___IN_DAYS:
        return inDays();
      case DomainPackage.TIME_SPAN___IN_WEEKS:
        return inWeeks();
      case DomainPackage.TIME_SPAN___AS_STRING:
        return asString();
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
    result.append(" (millis: ");
    result.append(millis);
    result.append(')');
    return result.toString();
  }

} //TimeSpanImpl
