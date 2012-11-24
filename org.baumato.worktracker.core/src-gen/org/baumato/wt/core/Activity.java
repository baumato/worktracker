/**
 */
package org.baumato.wt.core;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Activity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.baumato.wt.core.Activity#getActivityName <em>Activity Name</em>}</li>
 *   <li>{@link org.baumato.wt.core.Activity#getNext <em>Next</em>}</li>
 *   <li>{@link org.baumato.wt.core.Activity#getStart <em>Start</em>}</li>
 *   <li>{@link org.baumato.wt.core.Activity#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.baumato.wt.core.CorePackage#getActivity()
 * @model
 * @generated
 */
public interface Activity extends EObject
{
  /**
   * Returns the value of the '<em><b>Activity Name</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Activity Name</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Activity Name</em>' reference.
   * @see #setActivityName(ActivityName)
   * @see org.baumato.wt.core.CorePackage#getActivity_ActivityName()
   * @model
   * @generated
   */
  ActivityName getActivityName();

  /**
   * Sets the value of the '{@link org.baumato.wt.core.Activity#getActivityName <em>Activity Name</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Activity Name</em>' reference.
   * @see #getActivityName()
   * @generated
   */
  void setActivityName(ActivityName value);

  /**
   * Returns the value of the '<em><b>Next</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Next</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Next</em>' reference.
   * @see #setNext(Activity)
   * @see org.baumato.wt.core.CorePackage#getActivity_Next()
   * @model
   * @generated
   */
  Activity getNext();

  /**
   * Sets the value of the '{@link org.baumato.wt.core.Activity#getNext <em>Next</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Next</em>' reference.
   * @see #getNext()
   * @generated
   */
  void setNext(Activity value);

  /**
   * Returns the value of the '<em><b>Start</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Start</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Start</em>' reference.
   * @see #setStart(ActivityDate)
   * @see org.baumato.wt.core.CorePackage#getActivity_Start()
   * @model
   * @generated
   */
  ActivityDate getStart();

  /**
   * Sets the value of the '{@link org.baumato.wt.core.Activity#getStart <em>Start</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Start</em>' reference.
   * @see #getStart()
   * @generated
   */
  void setStart(ActivityDate value);

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
   * @see org.baumato.wt.core.CorePackage#getActivity_Description()
   * @model unique="false"
   * @generated
   */
  String getDescription();

  /**
   * Sets the value of the '{@link org.baumato.wt.core.Activity#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Description</em>' attribute.
   * @see #getDescription()
   * @generated
   */
  void setDescription(String value);

} // Activity
