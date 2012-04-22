<g:if test="${itemTypes?.size() > 0}">

<table class="adminTable">
	<thead>
	<tr>
		<th><g:message code="name"/></th>
		<th><g:message code="description"/></th>
		<th><g:message code="itemType.usable"/></th>
		<th><g:message code="itemType.uses"/></th>
		<th><g:message code="itemType.rechargeable"/></th>
		<th><g:message code="itemType.stackable"/></th>
		<th><g:message code="itemType.baseValue"/></th>
		<th><g:message code="itemType.availability"/></th>
		<th><g:message code="itemType.packageSize"/></th>
		<th><g:message code="itemType.combatDice"/></th>
		<th><g:message code="options"/></th>
	</tr>
	</thead>
	<tbody>
	<g:each in="${itemTypes}" var="itemType">
		<tr id="edit_${itemType.id}" class="editable_table">
			<g:render template="update" model="[itemType:itemType]"/>
		</tr>
	</g:each>
	</tbody>
</table>
	</g:if>
<g:else>
	<g:message code="itemType.admin.none.defined"/>
</g:else>