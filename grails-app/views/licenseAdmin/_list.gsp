<g:if test="${licenses?.size() > 0}">

<table class="adminTable">
	<thead>
	<tr>
		<th><g:message code="license.name"/></th>
		<th><g:message code="license.description"/></th>
		<th><g:message code="license.url"/></th>
		<th><g:message code="license.uses"/> </th>
		<th><g:message code="license.options"/></th>
	</tr>
	</thead>
	<tbody>
	<g:each in="${licenses}" var="license">
		<tr id="edit_${license.id}" class="editable_table">
			<g:render template="update" model="[license:license]"/>
		</tr>
	</g:each>
	</tbody>
</table>
	</g:if>
<g:else>
	<g:message code="license.admin.none.defined"/>
</g:else>