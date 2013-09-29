/**
 */
package org.tobbaumann.wt.domain;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Time Span</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.tobbaumann.wt.domain.TimeSpan#getMillis <em>Millis</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.TimeSpan#getSeconds <em>Seconds</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.TimeSpan#getMinutes <em>Minutes</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.TimeSpan#getHours <em>Hours</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.TimeSpan#getDays <em>Days</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.TimeSpan#getWeeks <em>Weeks</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.tobbaumann.wt.domain.DomainPackage#getTimeSpan()
 * @model
 * @generated
 */
public interface TimeSpan extends EObject
{
  /**
   * Returns the value of the '<em><b>Millis</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Millis</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Millis</em>' attribute.
   * @see #setMillis(long)
   * @see org.tobbaumann.wt.domain.DomainPackage#getTimeSpan_Millis()
   * @model unique="false"
   * @generated
   */
  long getMillis();

  /**
   * Sets the value of the '{@link org.tobbaumann.wt.domain.TimeSpan#getMillis <em>Millis</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Millis</em>' attribute.
   * @see #getMillis()
   * @generated
   */
  void setMillis(long value);

  /**
   * Returns the value of the '<em><b>Seconds</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Seconds</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Seconds</em>' attribute.
   * @see org.tobbaumann.wt.domain.DomainPackage#getTimeSpan_Seconds()
   * @model unique="false" transient="true" changeable="false" volatile="true" derived="true"
   *        annotation="http://www.eclipse.org/emf/2002/GenModel get='long _millis = this.getMillis();\nlong _divide = (_millis / 1000);\nlong _modulo = (_divide % 60);\nreturn <%java.lang.Long%>.valueOf(_modulo).intValue();'"
   * @generated
   */
  int getSeconds();

  /**
   * Returns the value of the '<em><b>Minutes</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Minutes</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Minutes</em>' attribute.
   * @see org.tobbaumann.wt.domain.DomainPackage#getTimeSpan_Minutes()
   * @model unique="false" transient="true" changeable="false" volatile="true" derived="true"
   *        annotation="http://www.eclipse.org/emf/2002/GenModel get='long _millis = this.getMillis();\nlong _divide = (_millis / 1000);\nlong _divide_1 = (_divide / 60);\nlong _modulo = (_divide_1 % 60);\nreturn <%java.lang.Long%>.valueOf(_modulo).intValue();'"
   * @generated
   */
  int getMinutes();

  /**
   * Returns the value of the '<em><b>Hours</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Hours</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Hours</em>' attribute.
   * @see org.tobbaumann.wt.domain.DomainPackage#getTimeSpan_Hours()
   * @model unique="false" transient="true" changeable="false" volatile="true" derived="true"
   *        annotation="http://www.eclipse.org/emf/2002/GenModel get='long _millis = this.getMillis();\nlong _divide = (_millis / 1000);\nlong _divide_1 = (_divide / 60);\nlong _divide_2 = (_divide_1 / 60);\nlong _modulo = (_divide_2 % 24);\nreturn <%java.lang.Long%>.valueOf(_modulo).intValue();'"
   * @generated
   */
  int getHours();

  /**
   * Returns the value of the '<em><b>Days</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Days</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Days</em>' attribute.
   * @see org.tobbaumann.wt.domain.DomainPackage#getTimeSpan_Days()
   * @model unique="false" transient="true" changeable="false" volatile="true" derived="true"
   *        annotation="http://www.eclipse.org/emf/2002/GenModel get='long _millis = this.getMillis();\nlong _divide = (_millis / 1000);\nlong _divide_1 = (_divide / 60);\nlong _divide_2 = (_divide_1 / 60);\nlong _divide_3 = (_divide_2 / 24);\nlong _modulo = (_divide_3 % 7);\nreturn <%java.lang.Long%>.valueOf(_modulo).intValue();'"
   * @generated
   */
  int getDays();

  /**
   * Returns the value of the '<em><b>Weeks</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Weeks</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Weeks</em>' attribute.
   * @see org.tobbaumann.wt.domain.DomainPackage#getTimeSpan_Weeks()
   * @model unique="false" transient="true" changeable="false" volatile="true" derived="true"
   *        annotation="http://www.eclipse.org/emf/2002/GenModel get='long _millis = this.getMillis();\nlong _divide = (_millis / 1000);\nlong _divide_1 = (_divide / 60);\nlong _divide_2 = (_divide_1 / 60);\nlong _divide_3 = (_divide_2 / 24);\nlong _divide_4 = (_divide_3 / 7);\nreturn <%java.lang.Long%>.valueOf(_divide_4).intValue();'"
   * @generated
   */
  int getWeeks();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model unique="false"
   *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.tobbaumann.wt.domain.TimeSpan%> _this = this;\nlong _millis = _this.getMillis();\nreturn (_millis / 1000);'"
   * @generated
   */
  long inSeconds();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model unique="false"
   *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.tobbaumann.wt.domain.TimeSpan%> _this = this;\nlong _inSeconds = _this.inSeconds();\nreturn (_inSeconds / 60);'"
   * @generated
   */
  long inMinutes();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model unique="false"
   *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.tobbaumann.wt.domain.TimeSpan%> _this = this;\nlong _inMinutes = _this.inMinutes();\nreturn (_inMinutes / 60);'"
   * @generated
   */
  long inHours();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model unique="false"
   *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.tobbaumann.wt.domain.TimeSpan%> _this = this;\nlong _inHours = _this.inHours();\nreturn (_inHours / 24);'"
   * @generated
   */
  long inDays();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model unique="false"
   *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.tobbaumann.wt.domain.TimeSpan%> _this = this;\nlong _inDays = _this.inDays();\nreturn (_inDays / 7);'"
   * @generated
   */
  long inWeeks();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model unique="false"
   *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%java.lang.StringBuilder%> _stringBuilder = new <%java.lang.StringBuilder%>();\nfinal <%java.lang.StringBuilder%> s = _stringBuilder;\n<%org.tobbaumann.wt.domain.TimeSpan%> _this = this;\nint _weeks = _this.getWeeks();\nboolean _greaterThan = (_weeks > 0);\nif (_greaterThan)\n{\n\t<%org.tobbaumann.wt.domain.TimeSpan%> _this_1 = this;\n\tint _weeks_1 = _this_1.getWeeks();\n\t<%java.lang.String%> _plus = (<%java.lang.Integer%>.valueOf(_weeks_1) + \"w \");\n\ts.append(_plus);\n}\n<%org.tobbaumann.wt.domain.TimeSpan%> _this_2 = this;\nint _days = _this_2.getDays();\nboolean _greaterThan_1 = (_days > 0);\nif (_greaterThan_1)\n{\n\t<%org.tobbaumann.wt.domain.TimeSpan%> _this_3 = this;\n\tint _days_1 = _this_3.getDays();\n\t<%java.lang.String%> _plus_1 = (<%java.lang.Integer%>.valueOf(_days_1) + \"d \");\n\ts.append(_plus_1);\n}\n<%org.tobbaumann.wt.domain.TimeSpan%> _this_4 = this;\nint _hours = _this_4.getHours();\nboolean _greaterThan_2 = (_hours > 0);\nif (_greaterThan_2)\n{\n\t<%org.tobbaumann.wt.domain.TimeSpan%> _this_5 = this;\n\tint _hours_1 = _this_5.getHours();\n\t<%java.lang.String%> _plus_2 = (<%java.lang.Integer%>.valueOf(_hours_1) + \"h \");\n\ts.append(_plus_2);\n}\n<%org.tobbaumann.wt.domain.TimeSpan%> _this_6 = this;\nint _minutes = _this_6.getMinutes();\nboolean _greaterThan_3 = (_minutes > 0);\nif (_greaterThan_3)\n{\n\t<%org.tobbaumann.wt.domain.TimeSpan%> _this_7 = this;\n\tint _minutes_1 = _this_7.getMinutes();\n\t<%java.lang.String%> _plus_3 = (<%java.lang.Integer%>.valueOf(_minutes_1) + \"m \");\n\ts.append(_plus_3);\n}\n<%org.tobbaumann.wt.domain.TimeSpan%> _this_8 = this;\nint _seconds = _this_8.getSeconds();\nboolean _greaterThan_4 = (_seconds > 0);\nif (_greaterThan_4)\n{\n\t<%org.tobbaumann.wt.domain.TimeSpan%> _this_9 = this;\n\tint _seconds_1 = _this_9.getSeconds();\n\t<%java.lang.String%> _plus_4 = (<%java.lang.Integer%>.valueOf(_seconds_1) + \"s \");\n\ts.append(_plus_4);\n}\n<%java.lang.String%> _string = s.toString();\nboolean _isEmpty = _string.isEmpty();\nif (_isEmpty)\n{\n\ts.append(\"0s\");\n}\n<%java.lang.String%> _string_1 = s.toString();\nreturn _string_1.trim();'"
   * @generated
   */
  String toString();

} // TimeSpan
