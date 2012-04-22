<g:if test="${questGivers?.size() > 0}">

<table class="adminTable">
	<thead>
	<tr>
		<th><g:message code="name"/></th>
		<th><g:message code="description"/></th>
		<th><g:message code="quest.giver.quests"/></th>
		<th><g:message code="options"/></th>
	</tr>
	</thead>
	<tbody>
	<g:each in="${questGivers}" var="questGiver">
		<tr id="edit_${questGiver.id}" class="editable_table">
			<g:render template="update" model="[questGiver:questGiver]"/>
		</tr>
	</g:each>
	</tbody>
</table>
	</g:if>
<g:else>
	<g:message code="questGiver.admin.none.defined"/>
</g:else>