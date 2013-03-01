/**
 */
package org.tobbaumann.wt.domain;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Activity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.tobbaumann.wt.domain.Activity#getId <em>Id</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.Activity#getName <em>Name</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.Activity#getOccurrenceFrequency <em>Occurrence Frequency</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.tobbaumann.wt.domain.DomainPackage#getActivity()
 * @model
 * @generated
 */
public interface Activity extends EObject
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
   * @see org.tobbaumann.wt.domain.DomainPackage#getActivity_Id()
   * @model unique="false"
   * @generated
   */
  String getId();

  /**
   * Sets the value of the '{@link org.tobbaumann.wt.domain.Activity#getId <em>Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Id</em>' attribute.
   * @see #getId()
   * @generated
   */
  void setId(String value);

  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see org.tobbaumann.wt.domain.DomainPackage#getActivity_Name()
   * @model unique="false"
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link org.tobbaumann.wt.domain.Activity#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Occurrence Frequency</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Occurrence Frequency</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Occurrence Frequency</em>' attribute.
   * @see #setOccurrenceFrequency(long)
   * @see org.tobbaumann.wt.domain.DomainPackage#getActivity_OccurrenceFrequency()
   * @model unique="false"
   * @generated
   */
  long getOccurrenceFrequency();

  /**
   * Sets the value of the '{@link org.tobbaumann.wt.domain.Activity#getOccurrenceFrequency <em>Occurrence Frequency</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Occurrence Frequency</em>' attribute.
   * @see #getOccurrenceFrequency()
   * @generated
   */
  void setOccurrenceFrequency(long value);

} // Activity
