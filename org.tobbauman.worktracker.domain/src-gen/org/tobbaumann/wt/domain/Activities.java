/**
 */
package org.tobbaumann.wt.domain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Activities</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.tobbaumann.wt.domain.Activities#getActivities <em>Activities</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.tobbaumann.wt.domain.DomainPackage#getActivities()
 * @model
 * @generated
 */
public interface Activities extends EObject
{
  /**
   * Returns the value of the '<em><b>Activities</b></em>' containment reference list.
   * The list contents are of type {@link org.tobbaumann.wt.domain.Activity}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Activities</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Activities</em>' containment reference list.
   * @see org.tobbaumann.wt.domain.DomainPackage#getActivities_Activities()
   * @model containment="true"
   * @generated
   */
  EList<Activity> getActivities();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model unique="false" numberOfActivitiesUnique="false"
   *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.tobbaumann.wt.domain.Activities%> _this = this;\n<%org.eclipse.emf.common.util.EList%><<%org.tobbaumann.wt.domain.Activity%>> _activities = _this.getActivities();\nfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.tobbaumann.wt.domain.Activity%>,<%java.lang.Long%>> _function = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.tobbaumann.wt.domain.Activity%>,<%java.lang.Long%>>()\n{\n\t\tpublic <%java.lang.Long%> apply(final <%org.tobbaumann.wt.domain.Activity%> it)\n\t\t{\n\t\t\tlong _occurrenceFrequency = it.getOccurrenceFrequency();\n\t\t\treturn <%java.lang.Long%>.valueOf(_occurrenceFrequency);\n\t\t}\n\t};\n<%java.util.List%><<%org.tobbaumann.wt.domain.Activity%>> _sortBy = <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.<<%org.tobbaumann.wt.domain.Activity%>, <%java.lang.Long%>>sortBy(_activities, _function);\n<%java.lang.Iterable%><<%org.tobbaumann.wt.domain.Activity%>> _take = <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.<<%org.tobbaumann.wt.domain.Activity%>>take(_sortBy, numberOfActivities);\n<%java.util.List%><<%org.tobbaumann.wt.domain.Activity%>> _list = <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.<<%org.tobbaumann.wt.domain.Activity%>>toList(_take);\n<%org.eclipse.emf.common.util.BasicEList%><<%org.tobbaumann.wt.domain.Activity%>> _basicEList = new <%org.eclipse.emf.common.util.BasicEList%><<%org.tobbaumann.wt.domain.Activity%>>(_list);\nreturn _basicEList;'"
   * @generated
   */
  EList<Activity> getMostUsedActivities(int numberOfActivities);

} // Activities
