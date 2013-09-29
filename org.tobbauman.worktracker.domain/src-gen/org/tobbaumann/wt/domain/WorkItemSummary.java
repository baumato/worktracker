/**
 */
package org.tobbaumann.wt.domain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Work Item Summary</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.tobbaumann.wt.domain.WorkItemSummary#getWorkItems <em>Work Items</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.WorkItemSummary#getActivityName <em>Activity Name</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.WorkItemSummary#getSumOfDurations <em>Sum Of Durations</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.WorkItemSummary#getSumOfDescriptions <em>Sum Of Descriptions</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.tobbaumann.wt.domain.DomainPackage#getWorkItemSummary()
 * @model
 * @generated
 */
public interface WorkItemSummary extends EObject
{
  /**
   * Returns the value of the '<em><b>Work Items</b></em>' reference list.
   * The list contents are of type {@link org.tobbaumann.wt.domain.WorkItem}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Work Items</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Work Items</em>' reference list.
   * @see org.tobbaumann.wt.domain.DomainPackage#getWorkItemSummary_WorkItems()
   * @model
   * @generated
   */
  EList<WorkItem> getWorkItems();

  /**
   * Returns the value of the '<em><b>Activity Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Activity Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Activity Name</em>' attribute.
   * @see org.tobbaumann.wt.domain.DomainPackage#getWorkItemSummary_ActivityName()
   * @model unique="false" transient="true" changeable="false" volatile="true" derived="true"
   *        annotation="http://www.eclipse.org/emf/2002/GenModel get='<%org.eclipse.emf.common.util.EList%><<%org.tobbaumann.wt.domain.WorkItem%>> _workItems = this.getWorkItems();\nboolean _isEmpty = _workItems.isEmpty();\nif (_isEmpty)\n{\n\treturn null;\n}\n<%org.eclipse.emf.common.util.EList%><<%org.tobbaumann.wt.domain.WorkItem%>> _workItems_1 = this.getWorkItems();\n<%org.tobbaumann.wt.domain.WorkItem%> _get = _workItems_1.get(0);\nreturn _get.getActivityName();'"
   * @generated
   */
  String getActivityName();

  /**
   * Returns the value of the '<em><b>Sum Of Durations</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Sum Of Durations</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Sum Of Durations</em>' containment reference.
   * @see org.tobbaumann.wt.domain.DomainPackage#getWorkItemSummary_SumOfDurations()
   * @model containment="true" transient="true" changeable="false" volatile="true" derived="true"
   *        annotation="http://www.eclipse.org/emf/2002/GenModel get='<%org.eclipse.emf.common.util.EList%><<%org.tobbaumann.wt.domain.WorkItem%>> _workItems = this.getWorkItems();\nfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.tobbaumann.wt.domain.WorkItem%>,<%java.lang.Long%>> _function = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.tobbaumann.wt.domain.WorkItem%>,<%java.lang.Long%>>()\n{\n\t\tpublic <%java.lang.Long%> apply(final <%org.tobbaumann.wt.domain.WorkItem%> it)\n\t\t{\n\t\t\t<%org.tobbaumann.wt.domain.TimeSpan%> _duration = it.getDuration();\n\t\t\tlong _millis = _duration.getMillis();\n\t\t\treturn <%java.lang.Long%>.valueOf(_millis);\n\t\t}\n\t};\n<%java.util.List%><<%java.lang.Long%>> _map = <%org.eclipse.xtext.xbase.lib.ListExtensions%>.<<%org.tobbaumann.wt.domain.WorkItem%>, <%java.lang.Long%>>map(_workItems, _function);\nfinal <%org.eclipse.xtext.xbase.lib.Functions.Function2%><<%java.lang.Long%>,<%java.lang.Long%>,<%java.lang.Long%>> _function_1 = new <%org.eclipse.xtext.xbase.lib.Functions.Function2%><<%java.lang.Long%>,<%java.lang.Long%>,<%java.lang.Long%>>()\n{\n\t\tpublic <%java.lang.Long%> apply(final <%java.lang.Long%> a, final <%java.lang.Long%> b)\n\t\t{\n\t\t\tlong _plus = ((a).longValue() + (b).longValue());\n\t\t\treturn <%java.lang.Long%>.valueOf(_plus);\n\t\t}\n\t};\nlong duration = (<%org.eclipse.xtext.xbase.lib.IterableExtensions%>.<<%java.lang.Long%>>reduce(_map, _function_1)).longValue();\nfinal <%org.tobbaumann.wt.domain.TimeSpan%> ts = <%org.tobbaumann.wt.domain.DomainFactory%>.eINSTANCE.createTimeSpan();\nts.setMillis(duration);\nreturn ts;'"
   * @generated
   */
  TimeSpan getSumOfDurations();

  /**
   * Returns the value of the '<em><b>Sum Of Descriptions</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Sum Of Descriptions</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Sum Of Descriptions</em>' attribute list.
   * @see org.tobbaumann.wt.domain.DomainPackage#getWorkItemSummary_SumOfDescriptions()
   * @model unique="false" transient="true" changeable="false" volatile="true" derived="true"
   *        annotation="http://www.eclipse.org/emf/2002/GenModel get='<%org.eclipse.emf.common.util.EList%><<%org.tobbaumann.wt.domain.WorkItem%>> _workItems = this.getWorkItems();\nfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.tobbaumann.wt.domain.WorkItem%>,<%java.lang.String%>> _function = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.tobbaumann.wt.domain.WorkItem%>,<%java.lang.String%>>()\n{\n\t\tpublic <%java.lang.String%> apply(final <%org.tobbaumann.wt.domain.WorkItem%> it)\n\t\t{\n\t\t\t<%java.lang.String%> _description = it.getDescription();\n\t\t\t<%java.lang.String%> _nullToEmpty = <%com.google.common.base.Strings%>.nullToEmpty(_description);\n\t\t\treturn _nullToEmpty;\n\t\t}\n\t};\n<%java.util.List%><<%java.lang.String%>> _map = <%org.eclipse.xtext.xbase.lib.ListExtensions%>.<<%org.tobbaumann.wt.domain.WorkItem%>, <%java.lang.String%>>map(_workItems, _function);\n<%org.eclipse.emf.common.util.BasicEList%><<%java.lang.String%>> _basicEList = new <%org.eclipse.emf.common.util.BasicEList%><<%java.lang.String%>>(_map);\nreturn _basicEList;'"
   * @generated
   */
  EList<String> getSumOfDescriptions();

} // WorkItemSummary
