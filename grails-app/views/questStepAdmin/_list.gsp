<g:if test="${questSteps?.size() > 0}">

<table class="adminTable">
	<thead>
	<tr>
		<th><g:message code="name"/></th>
		<th><g:message code="questStep.title"/></th>
		<th><g:message code="description"/></th>
		<th><g:message code="questStep.intro"/></th>
		<th><g:message code="questStep.endOfQuest"/></th>
		<th><g:message code="questStep.questTemplate"/></th>
		<th><g:message code="questStep.encounter"/></th>
		<th><g:message code="questStep.parentSteps"/></th>
		<th><g:message code="questStep.nextSteps"/></th>
		<th><g:message code="options"/></th>
	</tr>
	</thead>
	<tbody>
	<g:each in="${questSteps}" var="questStep">
		<tr id="edit_${questStep.id}" class="editable_table">
			<g:render template="update" model="[questStep:questStep]"/>
		</tr>
	</g:each>
	</tbody>
</table>
	</g:if>
<g:else>
	<g:message code="questStep.admin.none.defined"/>
</g:else>