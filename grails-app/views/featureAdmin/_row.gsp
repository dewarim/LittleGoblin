	<td>
		${feature.name}
	</td>
	<td>
		${feature.internalName}
	</td>
	<td>
		${feature.script.name}
	</td>
	<td>
		<g:remoteLink
				controller="featureAdmin" action="edit"
				update="[success:'edit_'+feature.id, failure:'message']"
				onSuccess="\$('#message').text('');"
				params="[id:feature.id]">
			[<g:message code="edit"/>]
		</g:remoteLink>
		&nbsp;
		<g:remoteLink
				controller="featureAdmin" action="delete"
				update="[success:'message', failure:'message']"
				params="[id:feature.id]"
				onSuccess="\$('#edit_${feature.id}').hide();">
			[<g:message code="delete"/>]
		</g:remoteLink>
	</td>
