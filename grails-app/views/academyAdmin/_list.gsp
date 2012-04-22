<g:if test="${academies?.size() > 0}">

<table class="adminTable">
	<thead>
	<tr>
		<th><g:message code="academy.name"/></th>
		<th><g:message code="academy.description"/></th>
		<th><g:message code="academy.town"/></th>
		<th><g:message code="academy.guilds"/></th>
		<th><g:message code="academy.options"/></th>
	</tr>
	</thead>
	<tbody>
	<g:each in="${academies}" var="academy">
		<tr id="edit_${academy.id}" class="editable_table">
			<g:render template="update" model="[academy:academy]"/>
		</tr>
	</g:each>
	</tbody>
</table>
	</g:if>
<g:else>
	<g:message code="academies.admin.none.defined"/>
</g:else>