<g:if test="${shopOwners?.size() > 0}">

<table class="adminTable">
	<thead>
	<tr>
		<th><g:message code="shopOwner.name"/></th>
		<th><g:message code="shopOwner.description"/></th>
		<th><g:message code="shopOwner.priceModifierDice"/></th>
		<th><g:message code="shopOwner.shops"/> </th>
		<th><g:message code="shopOwner.options"/></th>
	</tr>
	</thead>
	<tbody>
	<g:each in="${shopOwners}" var="shopOwner">
		<tr id="edit_${shopOwner.id}" class="editable_table">
			<g:render template="update" model="[shopOwner:shopOwner]"/>
		</tr>
	</g:each>
	</tbody>
</table>
	</g:if>
<g:else>
	<g:message code="admin.none.defined"/>
</g:else>