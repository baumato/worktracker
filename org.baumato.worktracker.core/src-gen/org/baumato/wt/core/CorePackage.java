/**
 */
package org.baumato.wt.core;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.baumato.wt.core.CoreFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel basePackage='org.baumato.wt'"
 * @generated
 */
public interface CorePackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "core";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "org.baumato.wt.core";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "core";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  CorePackage eINSTANCE = org.baumato.wt.core.impl.CorePackageImpl.init();

  /**
   * The meta object id for the '{@link org.baumato.wt.core.impl.ActivityImpl <em>Activity</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.baumato.wt.core.impl.ActivityImpl
   * @see org.baumato.wt.core.impl.CorePackageImpl#getActivity()
   * @generated
   */
  int ACTIVITY = 0;

  /**
   * The feature id for the '<em><b>Activity Name</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ACTIVITY__ACTIVITY_NAME = 0;

  /**
   * The feature id for the '<em><b>Next</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ACTIVITY__NEXT = 1;

  /**
   * The feature id for the '<em><b>Start</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ACTIVITY__START = 2;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ACTIVITY__DESCRIPTION = 3;

  /**
   * The number of structural features of the '<em>Activity</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ACTIVITY_FEATURE_COUNT = 4;

  /**
   * The number of operations of the '<em>Activity</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ACTIVITY_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link org.baumato.wt.core.impl.ActivityNameImpl <em>Activity Name</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.baumato.wt.core.impl.ActivityNameImpl
   * @see org.baumato.wt.core.impl.CorePackageImpl#getActivityName()
   * @generated
   */
  int ACTIVITY_NAME = 1;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ACTIVITY_NAME__NAME = 0;

  /**
   * The number of structural features of the '<em>Activity Name</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ACTIVITY_NAME_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Activity Name</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ACTIVITY_NAME_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link org.baumato.wt.core.impl.ActivityDateImpl <em>Activity Date</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.baumato.wt.core.impl.ActivityDateImpl
   * @see org.baumato.wt.core.impl.CorePackageImpl#getActivityDate()
   * @generated
   */
  int ACTIVITY_DATE = 2;

  /**
   * The feature id for the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ACTIVITY_DATE__DATE = 0;

  /**
   * The number of structural features of the '<em>Activity Date</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ACTIVITY_DATE_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Activity Date</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ACTIVITY_DATE_OPERATION_COUNT = 0;


  /**
   * Returns the meta object for class '{@link org.baumato.wt.core.Activity <em>Activity</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Activity</em>'.
   * @see org.baumato.wt.core.Activity
   * @generated
   */
  EClass getActivity();

  /**
   * Returns the meta object for the reference '{@link org.baumato.wt.core.Activity#getActivityName <em>Activity Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Activity Name</em>'.
   * @see org.baumato.wt.core.Activity#getActivityName()
   * @see #getActivity()
   * @generated
   */
  EReference getActivity_ActivityName();

  /**
   * Returns the meta object for the reference '{@link org.baumato.wt.core.Activity#getNext <em>Next</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Next</em>'.
   * @see org.baumato.wt.core.Activity#getNext()
   * @see #getActivity()
   * @generated
   */
  EReference getActivity_Next();

  /**
   * Returns the meta object for the reference '{@link org.baumato.wt.core.Activity#getStart <em>Start</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Start</em>'.
   * @see org.baumato.wt.core.Activity#getStart()
   * @see #getActivity()
   * @generated
   */
  EReference getActivity_Start();

  /**
   * Returns the meta object for the attribute '{@link org.baumato.wt.core.Activity#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see org.baumato.wt.core.Activity#getDescription()
   * @see #getActivity()
   * @generated
   */
  EAttribute getActivity_Description();

  /**
   * Returns the meta object for class '{@link org.baumato.wt.core.ActivityName <em>Activity Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Activity Name</em>'.
   * @see org.baumato.wt.core.ActivityName
   * @generated
   */
  EClass getActivityName();

  /**
   * Returns the meta object for the attribute '{@link org.baumato.wt.core.ActivityName#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.baumato.wt.core.ActivityName#getName()
   * @see #getActivityName()
   * @generated
   */
  EAttribute getActivityName_Name();

  /**
   * Returns the meta object for class '{@link org.baumato.wt.core.ActivityDate <em>Activity Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Activity Date</em>'.
   * @see org.baumato.wt.core.ActivityDate
   * @generated
   */
  EClass getActivityDate();

  /**
   * Returns the meta object for the attribute '{@link org.baumato.wt.core.ActivityDate#getDate <em>Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Date</em>'.
   * @see org.baumato.wt.core.ActivityDate#getDate()
   * @see #getActivityDate()
   * @generated
   */
  EAttribute getActivityDate_Date();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  CoreFactory getCoreFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each operation of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link org.baumato.wt.core.impl.ActivityImpl <em>Activity</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.baumato.wt.core.impl.ActivityImpl
     * @see org.baumato.wt.core.impl.CorePackageImpl#getActivity()
     * @generated
     */
    EClass ACTIVITY = eINSTANCE.getActivity();

    /**
     * The meta object literal for the '<em><b>Activity Name</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ACTIVITY__ACTIVITY_NAME = eINSTANCE.getActivity_ActivityName();

    /**
     * The meta object literal for the '<em><b>Next</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ACTIVITY__NEXT = eINSTANCE.getActivity_Next();

    /**
     * The meta object literal for the '<em><b>Start</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ACTIVITY__START = eINSTANCE.getActivity_Start();

    /**
     * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ACTIVITY__DESCRIPTION = eINSTANCE.getActivity_Description();

    /**
     * The meta object literal for the '{@link org.baumato.wt.core.impl.ActivityNameImpl <em>Activity Name</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.baumato.wt.core.impl.ActivityNameImpl
     * @see org.baumato.wt.core.impl.CorePackageImpl#getActivityName()
     * @generated
     */
    EClass ACTIVITY_NAME = eINSTANCE.getActivityName();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ACTIVITY_NAME__NAME = eINSTANCE.getActivityName_Name();

    /**
     * The meta object literal for the '{@link org.baumato.wt.core.impl.ActivityDateImpl <em>Activity Date</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.baumato.wt.core.impl.ActivityDateImpl
     * @see org.baumato.wt.core.impl.CorePackageImpl#getActivityDate()
     * @generated
     */
    EClass ACTIVITY_DATE = eINSTANCE.getActivityDate();

    /**
     * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ACTIVITY_DATE__DATE = eINSTANCE.getActivityDate_Date();

  }

} //CorePackage
