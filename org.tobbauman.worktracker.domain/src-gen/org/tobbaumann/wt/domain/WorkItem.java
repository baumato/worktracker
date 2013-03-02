/**
 */
package org.tobbaumann.wt.domain;

import java.lang.Comparable;

import java.text.DateFormat;

import java.util.Date;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Work Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.tobbaumann.wt.domain.WorkItem#getId <em>Id</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.WorkItem#getActivity <em>Activity</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.WorkItem#getActivityName <em>Activity Name</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.WorkItem#getStart <em>Start</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.WorkItem#getEnd <em>End</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.WorkItem#getDuration <em>Duration</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.WorkItem#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.tobbaumann.wt.domain.DomainPackage#getWorkItem()
 * @model superTypes="org.tobbaumann.wt.domain.Comparable"
 * @generated
 */
public interface WorkItem extends EObject, Comparable<WorkItem>
{
  /**
   * Returns the value of the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Id</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Id</em>' attribute.
   * @see #setId(String)
   * @see org.tobbaumann.wt.domain.DomainPackage#getWorkItem_Id()
   * @model unique="false" id="true"
   * @generated
   */
  String getId();

  /**
   * Sets the value of the '{@link org.tobbaumann.wt.domain.WorkItem#getId <em>Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Id</em>' attribute.
   * @see #getId()
   * @generated
   */
  void setId(String value);

  /**
   * Returns the value of the '<em><b>Activity</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Activity</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Activity</em>' reference.
   * @see #setActivity(Activity)
   * @see org.tobbaumann.wt.domain.DomainPackage#getWorkItem_Activity()
   * @model
   * @generated
   */
  Activity getActivity();

  /**
   * Sets the value of the '{@link org.tobbaumann.wt.domain.WorkItem#getActivity <em>Activity</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Activity</em>' reference.
   * @see #getActivity()
   * @generated
   */
  void setActivity(Activity value);

  /**
   * Returns the value of the '<em><b>Activity Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Activity Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Activity Name</em>' attribute.
   * @see org.tobbaumann.wt.domain.DomainPackage#getWorkItem_ActivityName()
   * @model unique="false" transient="true" changeable="false" volatile="true" derived="true"
   *        annotation="http://www.eclipse.org/emf/2002/GenModel get='<%java.lang.String%> _xifexpression = null;\n<%org.tobbaumann.wt.domain.Activity%> _activity = this.getActivity();\nboolean _notEquals = (!<%com.google.common.base.Objects%>.equal(_activity, null));\nif (_notEquals)\n{\n\t<%org.tobbaumann.wt.domain.Activity%> _activity_1 = this.getActivity();\n\t<%java.lang.String%> _name = _activity_1.getName();\n\t_xifexpression = _name;\n}\nelse\n{\n\t_xifexpression = null;\n}\nreturn _xifexpression;'"
   * @generated
   */
  String getActivityName();

  /**
   * Returns the value of the '<em><b>Start</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Start</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Start</em>' attribute.
   * @see #setStart(Date)
   * @see org.tobbaumann.wt.domain.DomainPackage#getWorkItem_Start()
   * @model unique="false" dataType="org.tobbaumann.wt.domain.Date"
   * @generated
   */
  Date getStart();

  /**
   * Sets the value of the '{@link org.tobbaumann.wt.domain.WorkItem#getStart <em>Start</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Start</em>' attribute.
   * @see #getStart()
   * @generated
   */
  void setStart(Date value);

  /**
   * Returns the value of the '<em><b>End</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>End</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>End</em>' attribute.
   * @see #setEnd(Date)
   * @see org.tobbaumann.wt.domain.DomainPackage#getWorkItem_End()
   * @model unique="false" dataType="org.tobbaumann.wt.domain.Date"
   * @generated
   */
  Date getEnd();

  /**
   * Sets the value of the '{@link org.tobbaumann.wt.domain.WorkItem#getEnd <em>End</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>End</em>' attribute.
   * @see #getEnd()
   * @generated
   */
  void setEnd(Date value);

  /**
   * Returns the value of the '<em><b>Duration</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Duration</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Duration</em>' containment reference.
   * @see org.tobbaumann.wt.domain.DomainPackage#getWorkItem_Duration()
   * @model containment="true" transient="true" changeable="false" volatile="true" derived="true"
   *        annotation="http://www.eclipse.org/emf/2002/GenModel get='final <%org.tobbaumann.wt.domain.TimeSpan%> ts = <%org.tobbaumann.wt.domain.DomainFactory%>.eINSTANCE.createTimeSpan();\n<%java.util.Date%> _xifexpression = null;\n<%java.util.Date%> _start = this.getStart();\nboolean _equals = <%com.google.common.base.Objects%>.equal(_start, null);\nif (_equals)\n{\n\t<%java.util.Date%> _date = new <%java.util.Date%>();\n\t_xifexpression = _date;\n}\nelse\n{\n\t<%java.util.Date%> _start_1 = this.getStart();\n\t_xifexpression = _start_1;\n}\nfinal <%java.util.Date%> s = _xifexpression;\n<%java.util.Date%> _xifexpression_1 = null;\n<%java.util.Date%> _end = this.getEnd();\nboolean _equals_1 = <%com.google.common.base.Objects%>.equal(_end, null);\nif (_equals_1)\n{\n\t_xifexpression_1 = s;\n}\nelse\n{\n\t<%java.util.Date%> _end_1 = this.getEnd();\n\t_xifexpression_1 = _end_1;\n}\nfinal <%java.util.Date%> e = _xifexpression_1;\nfinal <%org.tobbaumann.wt.domain.impl.TimeSpanHelper%> h = <%org.tobbaumann.wt.domain.impl.TimeSpanHelper%>.getInstance(s, e);\nint _inSeconds = h.inSeconds();\nint _multiply = (_inSeconds * 1000);\nts.setMillis(_multiply);\nreturn ts;'"
   * @generated
   */
  TimeSpan getDuration();

  /**
   * Returns the value of the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Description</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Description</em>' attribute.
   * @see #setDescription(String)
   * @see org.tobbaumann.wt.domain.DomainPackage#getWorkItem_Description()
   * @model unique="false"
   * @generated
   */
  String getDescription();

  /**
   * Sets the value of the '{@link org.tobbaumann.wt.domain.WorkItem#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Description</em>' attribute.
   * @see #getDescription()
   * @generated
   */
  void setDescription(String value);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model unique="false" dfDataType="org.tobbaumann.wt.domain.DateFormat" dfUnique="false"
   *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.tobbaumann.wt.domain.WorkItem%> _this = this;\n<%java.util.Date%> _start = _this.getStart();\nreturn df.format(_start);'"
   * @generated
   */
  String formatStart(DateFormat df);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model unique="false" dfDataType="org.tobbaumann.wt.domain.DateFormat" dfUnique="false"
   *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.tobbaumann.wt.domain.WorkItem%> _this = this;\n<%java.util.Date%> _end = _this.getEnd();\nreturn df.format(_end);'"
   * @generated
   */
  String formatEnd(DateFormat df);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model unique="false" wiUnique="false"
   *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.tobbaumann.wt.domain.WorkItem%> _this = this;\n<%java.util.Date%> _start = _this.getStart();\n<%java.util.Date%> _start_1 = wi.getStart();\nreturn _start.compareTo(_start_1);'"
   * @generated
   */
  int compareTo(WorkItem wi);

} // WorkItem
