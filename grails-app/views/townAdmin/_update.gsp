	<td>
		${town.name}
	</td>
	<td>
		${town.description}
	</td>
	<td>
		${town.shortDescription}
	</td>
	<td>
		<ul>
			<g:each in="${town.academies}" var="academy">
				<li>${academy.name}</li>
			</g:each>
		</ul>
	</td>
	<td>
		<ul>
			<g:each in="${town.shops}" var="shop">
				<li>${shop.name}</li>
			</g:each>
		</ul>
	</td>

	<td>
		<g:remoteLink
				controller="townAdmin" action="edit"
				update="[success:'edit_'+town.id, failure:'message']"
				onSuccess="\$('#message').text('');"
				params="[id:town.id]">
			[<g:message code="edit"/>]
		</g:remoteLink>
		&nbsp;
		<g:remoteLink
				controller="townAdmin" action="delete"
				update="[success:'message', failure:'message']"
				params="[id:town.id]"
				onSuccess="\$('#edit_${town.id}').hide();">
			[<g:message code="delete"/>]
		</g:remoteLink>
	</td>
