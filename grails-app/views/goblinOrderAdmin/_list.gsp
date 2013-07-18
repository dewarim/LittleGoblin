<g:if test="${goblinOrders?.size() > 0}">

<table class="adminTable">
	<thead>
	<tr>
		<th><g:message code="name"/></th>
		<th><g:message code="description"/></th>
		<th><g:message code="goblinOrder.score"/></th>
		<th><g:message code="goblinOrder.coins"/></th>
		<th><g:message code="goblinOrder.leader"/></th>
		<th><g:message code="goblinOrder.options"/></th>
	</tr>
	</thead>
	<tbody>
	<g:each in="${goblinOrders}" var="goblinOrder">
		<tr id="edit_${goblinOrder.id}" class="editable_table">
			<g:render template="update" model="[goblinOrder:goblinOrder]"/>
		</tr>
	</g:each>
	</tbody>
</table>
	</g:if>
<g:else>
	<g:message code="goblinOrder.admin.none.defined"/>
</g:else>