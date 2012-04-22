<td>
	${category.name}
</td>
<td>
	<g:if test="${category.description}">
		<p>${category.description}</p>
		<hr>
		<p><em><g:message code="productCategory.desc.translation"/></em>:</p>
		<p class="category_description"><g:message code="${category.description}"/></p>
	</g:if>

</td>
<td class="where_used_list">
	<ul>
		<g:each in="${category.products}" var="product">
			<li><g:message code="${product.name}"/></li>
		</g:each>
	</ul>
</td>
<td>
	<g:remoteLink
			controller="productCategoryAdmin" action="edit"
			update="[success:'edit_'+category.id, failure:'message']"
			onSuccess="\$('#message').text('');"
			params="[id:category.id]">
		[<g:message code="edit"/>]
	</g:remoteLink>
	&nbsp;
	<g:remoteLink
			controller="productCategoryAdmin" action="delete"
			update="[success:'message', failure:'message']"
			params="[id:category.id]"
			onSuccess="\$('#edit_${category.id}').hide();">
		[<g:message code="delete"/>]
	</g:remoteLink>
</td>
