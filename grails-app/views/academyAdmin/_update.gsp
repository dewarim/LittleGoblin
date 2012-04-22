	<td>
		${academy.name}
	</td>
	<td>
		${academy.description}
	</td>
	<td>
		${academy.town.name}
	</td>
	<td>
		<ul>
			<g:each in="${academy.guildAcademies}" var="guildAcademy">
				<li>${guildAcademy.guild.name}</li>
			</g:each>
		</ul>
	</td>
	<td>
		<g:remoteLink
				controller="academyAdmin" action="edit"
				update="[success:'edit_'+academy.id, failure:'message']"
				onSuccess="\$('#message').text('');"
				params="[id:academy.id]">
			[<g:message code="edit"/>]
		</g:remoteLink>
		&nbsp;
		<g:remoteLink
				controller="academyAdmin" action="delete"
				update="[success:'message', failure:'message']"
				params="[id:academy.id]"
				onSuccess="\$('#edit_${academy.id}').hide();\$('#message').text('');">
			[<g:message code="delete"/>]
		</g:remoteLink>
	</td>
