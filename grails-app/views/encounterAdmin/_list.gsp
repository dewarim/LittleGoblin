<g:if test="${encounters?.size() > 0}">

<table class="adminTable">
	<thead>
	<tr>
		<th><g:message code="name"/></th>
		<th><g:message code="encounter.includesCombat"/></th>
		<th><g:message code="encounter.script"/></th>
		<th><g:message code="encounter.config"/></th>
		<th><g:message code="encounter.mobs"/></th>
		<th><g:message code="encounter.steps"/></th>
		<th><g:message code="options"/></th>
	</tr>
	</thead>
	<tbody>
	<g:each in="${encounters}" var="encounter">
		<tr id="edit_${encounter.id}" class="editable_table">
			<g:render template="update" model="[encounter:encounter]"/>
		</tr>
	</g:each>
	</tbody>
</table>
	</g:if>
<g:else>
	<g:message code="admin.none.defined"/>
</g:else>