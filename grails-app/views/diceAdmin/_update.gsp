	<td>
		${dice.name}
	</td>
	<td>
		${dice.amount}
	</td>
	<td>
		${dice.sides}
	</td>
	<td>
		${dice.bonus}
	</td>

	<td>
		<g:remoteLink
				controller="diceAdmin" action="edit"
				update="[success:'edit_'+dice.id, failure:'message']"
				onSuccess="\$('#message').text('');"
				params="[id:dice.id]">
			[<g:message code="edit"/>]
		</g:remoteLink>
		&nbsp;
		<g:remoteLink
				controller="diceAdmin" action="delete"
				update="[success:'message', failure:'message']"
				params="[id:dice.id]"
				onSuccess="\$('#edit_${dice.id}').hide();">
			[<g:message code="delete"/>]
		</g:remoteLink>
	</td>
