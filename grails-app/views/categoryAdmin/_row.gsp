	<td>
		${category.name}
	</td>
	<td>
		${category.shopCategories?.size()}
	</td>
	<td>
		${category.itemCategories?.size()}
	</td>
	<td>
		<g:remoteLink
				controller="categoryAdmin" action="edit"
				update="[success:'edit_'+category.id, failure:'message']"
				onSuccess="\$('#message').text('');"
				params="[id:category.id]">
			[<g:message code="edit"/>]
		</g:remoteLink>
		&nbsp;
		<g:remoteLink
				controller="categoryAdmin" action="delete"
				update="[success:'message', failure:'message']"
				params="[id:category.id]"
				onSuccess="\$('#edit_${category.id}').hide();">
			[<g:message code="delete"/>]
		</g:remoteLink>
	</td>
