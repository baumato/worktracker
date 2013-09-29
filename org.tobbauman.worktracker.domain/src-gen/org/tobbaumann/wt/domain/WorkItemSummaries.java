/**
 */
package org.tobbaumann.wt.domain;

import java.math.BigDecimal;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Work Item Summaries</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.tobbaumann.wt.domain.WorkItemSummaries#getWorkItemSummaries <em>Work Item Summaries</em>}</li>
 *   <li>{@link org.tobbaumann.wt.domain.WorkItemSummaries#getSumOfDurations <em>Sum Of Durations</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.tobbaumann.wt.domain.DomainPackage#getWorkItemSummaries()
 * @model
 * @generated
 */
public interface WorkItemSummaries extends EObject
{
  /**
   * Returns the value of the '<em><b>Work Item Summaries</b></em>' reference list.
   * The list contents are of type {@link org.tobbaumann.wt.domain.WorkItemSummary}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Work Item Summaries</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Work Item Summaries</em>' reference list.
   * @see org.tobbaumann.wt.domain.DomainPackage#getWorkItemSummaries_WorkItemSummaries()
   * @model
   * @generated
   */
  EList<WorkItemSummary> getWorkItemSummaries();

  /**
   * Returns the value of the '<em><b>Sum Of Durations</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Sum Of Durations</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Sum Of Durations</em>' containment reference.
   * @see org.tobbaumann.wt.domain.DomainPackage#getWorkItemSummaries_SumOfDurations()
   * @model containment="true" transient="true" changeable="false" volatile="true" derived="true"
   *        annotation="http://www.eclipse.org/emf/2002/GenModel get='<%org.eclipse.emf.common.util.EList%><<%org.tobbaumann.wt.domain.WorkItemSummary%>> _workItemSummaries = this.getWorkItemSummaries();\nfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.tobbaumann.wt.domain.WorkItemSummary%>,<%java.lang.Long%>> _function = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.tobbaumann.wt.domain.WorkItemSummary%>,<%java.lang.Long%>>()\n{\n\t\tpublic <%java.lang.Long%> apply(final <%org.tobbaumann.wt.domain.WorkItemSummary%> it)\n\t\t{\n\t\t\t<%org.tobbaumann.wt.domain.TimeSpan%> _sumOfDurations = it.getSumOfDurations();\n\t\t\tlong _millis = _sumOfDurations.getMillis();\n\t\t\treturn <%java.lang.Long%>.valueOf(_millis);\n\t\t}\n\t};\n<%java.util.List%><<%java.lang.Long%>> _map = <%org.eclipse.xtext.xbase.lib.ListExtensions%>.<<%org.tobbaumann.wt.domain.WorkItemSummary%>, <%java.lang.Long%>>map(_workItemSummaries, _function);\nfinal <%org.eclipse.xtext.xbase.lib.Functions.Function2%><<%java.lang.Long%>,<%java.lang.Long%>,<%java.lang.Long%>> _function_1 = new <%org.eclipse.xtext.xbase.lib.Functions.Function2%><<%java.lang.Long%>,<%java.lang.Long%>,<%java.lang.Long%>>()\n{\n\t\tpublic <%java.lang.Long%> apply(final <%java.lang.Long%> a, final <%java.lang.Long%> b)\n\t\t{\n\t\t\tlong _plus = ((a).longValue() + (b).longValue());\n\t\t\treturn <%java.lang.Long%>.valueOf(_plus);\n\t\t}\n\t};\nfinal long duration = (<%org.eclipse.xtext.xbase.lib.IterableExtensions%>.<<%java.lang.Long%>>reduce(_map, _function_1)).longValue();\nfinal <%org.tobbaumann.wt.domain.TimeSpan%> ts = <%org.tobbaumann.wt.domain.DomainFactory%>.eINSTANCE.createTimeSpan();\nts.setMillis(duration);\nreturn ts;'"
   * @generated
   */
  TimeSpan getSumOfDurations();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model unique="false" wisUnique="false"
   *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.tobbaumann.wt.domain.WorkItemSummaries%> _this = this;\nfinal <%org.tobbaumann.wt.domain.TimeSpan%> durationAll = _this.getSumOfDurations();\nlong _millis = durationAll.getMillis();\nboolean _equals = (_millis == 0);\nif (_equals)\n{\n\treturn <%java.math.BigDecimal%>.ZERO;\n}\n<%org.tobbaumann.wt.domain.TimeSpan%> _sumOfDurations = wis.getSumOfDurations();\nlong _millis_1 = _sumOfDurations.getMillis();\n<%java.math.BigDecimal%> _bigDecimal = new <%java.math.BigDecimal%>(_millis_1);\nfinal <%java.math.BigDecimal%> durationSingle = _bigDecimal;\nlong _millis_2 = durationAll.getMillis();\n<%java.math.BigDecimal%> _bigDecimal_1 = new <%java.math.BigDecimal%>(_millis_2);\nfinal <%java.math.BigDecimal%> durationAllMillis = _bigDecimal_1;\n<%java.math.BigDecimal%> _divide = durationSingle.divide(durationAllMillis, 10, <%java.math.RoundingMode%>.HALF_UP);\n<%java.math.BigDecimal%> _bigDecimal_2 = new <%java.math.BigDecimal%>(100);\nfinal <%java.math.BigDecimal%> ratio = _divide.multiply(_bigDecimal_2);\nreturn ratio;'"
   * @generated
   */
  BigDecimal computeDurationRatioInPercent(WorkItemSummary wis);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model unique="false" wisUnique="false" totalMinutesUnique="false"
   *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.tobbaumann.wt.domain.WorkItemSummaries%> _this = this;\nfinal <%java.math.BigDecimal%> ratio = _this.computeDurationRatioInPercent(wis);\n<%java.math.BigDecimal%> _bigDecimal = new <%java.math.BigDecimal%>(totalMinutes);\nint _multiply = (60 * 1000);\n<%java.math.BigDecimal%> _bigDecimal_1 = new <%java.math.BigDecimal%>(_multiply);\nfinal <%java.math.BigDecimal%> totalMillis = _bigDecimal.multiply(_bigDecimal_1);\n<%java.math.BigDecimal%> _bigDecimal_2 = new <%java.math.BigDecimal%>(100);\n<%java.math.BigDecimal%> _divide = ratio.divide(_bigDecimal_2, 10, <%java.math.RoundingMode%>.HALF_UP);\nfinal <%java.math.BigDecimal%> ratioMillis = _divide.multiply(totalMillis);\nfinal <%org.tobbaumann.wt.domain.TimeSpan%> ts = <%org.tobbaumann.wt.domain.DomainFactory%>.eINSTANCE.createTimeSpan();\nlong _longValue = ratioMillis.longValue();\nts.setMillis(_longValue);\nreturn ts;'"
   * @generated
   */
  TimeSpan computeDurationRatio(WorkItemSummary wis, int totalMinutes);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model unique="false" totalMinutesUnique="false"
   *        annotation="http://www.eclipse.org/emf/2002/GenModel body='long millis = 0;\n<%org.tobbaumann.wt.domain.WorkItemSummaries%> _this = this;\n<%org.eclipse.emf.common.util.EList%><<%org.tobbaumann.wt.domain.WorkItemSummary%>> _workItemSummaries = _this.getWorkItemSummaries();\nfor (final <%org.tobbaumann.wt.domain.WorkItemSummary%> wis : _workItemSummaries)\n{\n\t<%org.tobbaumann.wt.domain.WorkItemSummaries%> _this_1 = this;\n\t<%org.tobbaumann.wt.domain.TimeSpan%> _computeDurationRatio = _this_1.computeDurationRatio(wis, totalMinutes);\n\tlong _millis = _computeDurationRatio.getMillis();\n\tlong _plus = (millis + _millis);\n\tmillis = _plus;\n}\nfinal <%org.tobbaumann.wt.domain.TimeSpan%> ts = <%org.tobbaumann.wt.domain.DomainFactory%>.eINSTANCE.createTimeSpan();\nts.setMillis(millis);\nreturn ts;'"
   * @generated
   */
  TimeSpan computeSumOfDurationRatio(int totalMinutes);

} // WorkItemSummaries
