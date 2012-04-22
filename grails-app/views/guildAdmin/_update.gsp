	<td>
		${guild.name}
	</td>
	<td>
		${guild.description}
	</td>
	<td>
		${guild.entryFee}
	</td>
	<td>
		${guild.incomeTax}
	</td>
	<td>
		<ul>
			<g:each in="${guild.guildAcademies}" var="guildAcademy">
				<li>${guildAcademy.academy.name}</li>
			</g:each>
		</ul>
	</td>
	<td>
		<g:remoteLink
				controller="guildAdmin" action="edit"
				update="[success:'edit_'+guild.id, failure:'message']"
				onSuccess="\$('#message').text('');"
				params="[id:guild.id]">
			[<g:message code="edit"/>]
		</g:remoteLink>
		&nbsp;
		<g:remoteLink
				controller="guildAdmin" action="delete"
				update="[success:'message', failure:'message']"
				params="[id:guild.id]"
				onSuccess="\$('#edit_${guild.id}').hide();\$('#message').text('');">
			[<g:message code="delete"/>]
		</g:remoteLink>
	</td>
