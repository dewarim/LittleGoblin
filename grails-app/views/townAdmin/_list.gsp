<g:if test="${towns?.size() > 0}">

<table class="adminTable">
	<thead>
	<tr>
		<th><g:message code="town.name"/></th>
		<th><g:message code="town.description"/></th>
		<th><g:message code="town.shortDescription"/></th>
		<th><g:message code="town.academies"/> </th>
		<th><g:message code="town.shops"/> </th>
		<th><g:message code="town.options"/></th>
	</tr>
	</thead>
	<tbody>
	<g:each in="${towns}" var="town">
		<tr id="edit_${town.id}" class="editable_table">
			<g:render template="update" model="[town:town]"/>
		</tr>
	</g:each>
	</tbody>
</table>
	</g:if>
<g:else>
	<g:message code="admin.none.defined"/>
</g:else>