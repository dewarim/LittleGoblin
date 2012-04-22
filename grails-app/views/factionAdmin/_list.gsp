<g:if test="${factions?.size() > 0}">

	<table class="adminTable">
		<thead>
		<tr>
			<g:sortableColumn property="name" titleKey="faction.name" defaultOrder="desc" action="index"/>
			<th> <g:message code="faction.description"/> </th>
			<th> <g:message code="faction.start_level"/> </th>
			<th> <g:message code="faction.repMessageMap"/> </th>
			<th> <g:message code="faction.options"/> </th>
		</tr>
		</thead>
		<tbody>
		<g:each in="${factions}" var="faction">
			<tr id="edit_${faction.id}" class="editable_table">
				<g:render template="update" model="[faction:faction]"/>
			</tr>
		</g:each>
		</tbody>
	</table>
</g:if>
<g:else>
	<g:message code="faction.admin.none.defined"/>
</g:else>