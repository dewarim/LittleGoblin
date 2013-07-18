	<td>
		${goblinOrder.name}
	</td>
	<td>
		${goblinOrder.description}
	</td>
	<td>
		${goblinOrder.score}
	</td>
	<td>
		${goblinOrder.coins}
	</td>
	<td>
		${goblinOrder?.leader?.name}
	</td>
	<td>
		<g:remoteLink
				controller="goblinOrderAdmin" action="edit"
				update="[success:'edit_'+goblinOrder.id, failure:'message']"
				onSuccess="\$('#message').text('');"
				params="[id:goblinOrder.id]">
			[<g:message code="edit"/>]
		</g:remoteLink>
		&nbsp;
		<g:remoteLink
				controller="goblinOrderAdmin" action="delete"
				update="[success:'message', failure:'message']"
				params="[id:goblinOrder.id]"
				onSuccess="\$('#edit_${goblinOrder.id}').hide();\$('#message').text('');">
			[<g:message code="delete"/>]
		</g:remoteLink>
	</td>
