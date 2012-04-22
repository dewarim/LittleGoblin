	<td>
		${configEntry.name}
	</td>
	<td>
		${configEntry.description}
	</td>
	<td>
		${configEntry.entryValue}
	</td>
	<td>
		<g:remoteLink
				controller="configAdmin" action="edit"
				update="[success:'edit_'+configEntry.id, failure:'message']"
				onSuccess="\$('#message').text('');"
				params="[id:configEntry.id]">
			[<g:message code="edit"/>]
		</g:remoteLink>
		&nbsp;
		<g:remoteLink
				controller="configAdmin" action="delete"
				update="[success:'message', failure:'message']"
				params="[id:configEntry.id]"
				onSuccess="\$('#edit_${configEntry.id}').hide();">
			[<g:message code="delete"/>]
		</g:remoteLink>
	</td>
