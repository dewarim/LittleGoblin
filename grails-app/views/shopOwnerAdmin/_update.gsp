	<td>
		${shopOwner.name}
	</td>
	<td>
		${shopOwner.description}
	</td>
	<td>
		${shopOwner.priceModifierDice.name}<br><em>${shopOwner.priceModifierDice}</em>
	</td>
	<td>
		<ul>
			<g:each in="${shopOwner.shops}" var="shop">
				<li>${shop.name} (<g:message code="${shop.town.name}"/>)</li>
			</g:each>
		</ul>
	</td>

	<td>
		<g:remoteLink
				controller="shopOwnerAdmin" action="edit"
				update="[success:'edit_'+shopOwner.id, failure:'message']"
				onSuccess="\$('#message').text('');"
				params="[id:shopOwner.id]">
			[<g:message code="edit"/>]
		</g:remoteLink>
		&nbsp;
		<g:remoteLink
				controller="shopOwnerAdmin" action="delete"
				update="[success:'message', failure:'message']"
				params="[id:shopOwner.id]"
				onSuccess="\$('#edit_${shopOwner.id}').hide();">
			[<g:message code="delete"/>]
		</g:remoteLink>
	</td>
