<g:if test="${shops?.size() > 0}">

<table class="adminTable">
	<thead>
	<tr>
		<th><g:message code="name"/></th>
		<th><g:message code="description"/></th>
		<th><g:message code="shop.owner"/></th>
		<th><g:message code="shop.town"/></th>
		<th><g:message code="options"/></th>
	</tr>
	</thead>
	<tbody>
	<g:each in="${shops}" var="shop">
		<tr id="edit_${shop.id}" class="editable_table">
			<g:render template="update" model="[shop:shop]"/>
		</tr>
	</g:each>
	</tbody>
</table>
	</g:if>
<g:else>
	<g:message code="admin.none.defined"/>
</g:else>