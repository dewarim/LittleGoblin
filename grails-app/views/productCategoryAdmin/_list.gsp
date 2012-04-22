<g:if test="${categories?.size() > 0}">

<table class="adminTable">
	<thead>
	<tr>
		<th><g:message code="name"/></th>
		<th><g:message code="description"/></th>
		<th><g:message code="product.category.products"/></th>
		<th><g:message code="options"/></th>
	</tr>
	</thead>
	<tbody>
	<g:each in="${categories}" var="category">
		<tr id="edit_${category.id}" class="editable_table">
			<g:render template="update" model="[category:category]"/>
		</tr>
	</g:each>
	</tbody>
</table>
	</g:if>
<g:else>
	<g:message code="category.admin.none.defined"/>
</g:else>