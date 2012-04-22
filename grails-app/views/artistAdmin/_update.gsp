	<td>
		${artist.name}
	</td>
	<td>
		<a href="${artist.website}" target="_blank">${artist.website}</a>
	</td>
	<td>
		#todo: link to images of this artist
	</td>
	<td>
		<g:remoteLink
				controller="artistAdmin" action="edit"
				update="[success:'edit_'+artist.id, failure:'message']"
				onSuccess="\$('#message').text('');"
				params="[id:artist.id]">
			[<g:message code="edit"/>]
		</g:remoteLink>
		&nbsp;
		<g:remoteLink
				controller="artistAdmin" action="delete"
				update="[success:'message', failure:'message']"
				params="[id:artist.id]"
				onSuccess="\$('#edit_${artist.id}').hide();">
			[<g:message code="delete"/>]
		</g:remoteLink>
	</td>
