<g:if test="${products?.size() > 0}">

<table class="adminTable">
	<thead>
	<tr>
		<th><g:message code="product.name"/></th>
		<th><g:message code="product.timeNeeded"/></th>
		<th><g:message code="product.category"/></th>
		<th><g:message code="product.components"/></th>
		<th><g:message code="product.skills"/> </th>
		<th><g:message code="options"/></th>
	</tr>
	</thead>
	<tbody>
	<g:each in="${products}" var="product">
		<tr id="edit_${product.id}" class="editable_table">
			<g:render template="update" model="[product:product]"/>
		</tr>
	</g:each>
	</tbody>
</table>
	</g:if>
<g:else>
	<g:message code="admin.none.defined"/>
</g:else>