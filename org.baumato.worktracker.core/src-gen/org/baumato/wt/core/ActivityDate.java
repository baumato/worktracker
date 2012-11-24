/**
 */
package org.baumato.wt.core;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Activity Date</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.baumato.wt.core.ActivityDate#getDate <em>Date</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.baumato.wt.core.CorePackage#getActivityDate()
 * @model
 * @generated
 */
public interface ActivityDate extends EObject
{
  /**
   * Returns the value of the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Date</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Date</em>' attribute.
   * @see #setDate(String)
   * @see org.baumato.wt.core.CorePackage#getActivityDate_Date()
   * @model unique="false"
   * @generated
   */
  String getDate();

  /**
   * Sets the value of the '{@link org.baumato.wt.core.ActivityDate#getDate <em>Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Date</em>' attribute.
   * @see #getDate()
   * @generated
   */
  void setDate(String value);

} // ActivityDate
