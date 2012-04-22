	<td>
		${faction.name}
		<br>
		<em><g:message code="${faction.name}"/> </em>
	</td>
	<td>
		${faction.description}
		<br>
		<em><g:message code="${faction.description}"/> </em>
	</td>
	<td>
		${faction.startLevel}
	</td>
	<td>
		${faction.repMessageMap?.name}
	</td>

	<td>
		<g:remoteLink
				controller="factionAdmin" action="edit"
				update="[success:'edit_'+faction.id, failure:'message']"
				onSuccess="\$('#message').text('');"
				params="[id:faction.id]">
			[<g:message code="edit"/>]
		</g:remoteLink>
		&nbsp;
		<g:remoteLink
				controller="factionAdmin" action="delete"
				update="[success:'message', failure:'message']"
				params="[id:faction.id]"
				onSuccess="\$('#edit_${faction.id}').hide();">
			[<g:message code="delete"/>]
		</g:remoteLink>
	</td>
