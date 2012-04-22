<td>
	${itemType.name}
</td>
<td>
	<g:if test="${itemType.description}">
		<p>${itemType.description}</p>
		<hr>
		<p><em><g:message code="desc.translation"/></em>:</p>
		<p class="itemType_description"><g:message code="${itemType.description}"/></p>
	</g:if>
</td>
<td class="center">
	${itemType.usable}
</td>
<td class="center">
	${itemType.uses}
</td>
<td class="center">
	${itemType.rechargeable}
</td>
<td class="center">
	${itemType.stackable}
</td>
<td class="center">
   ${itemType.baseValue}
</td>
<td class="center">
   ${itemType.availability}
</td>
<td class="center">
   ${itemType.packageSize}
</td>
<td class="center">
   ${itemType.combatDice}
</td>
<td>
	<g:remoteLink
			controller="itemAdmin" action="edit"
			update="[success:'edit_'+itemType.id, failure:'message']"
			onSuccess="\$('#message').text('');"
			params="[id:itemType.id]">
		[<g:message code="edit"/>]
	</g:remoteLink>
	&nbsp;
	<g:remoteLink
			controller="itemAdmin" action="delete"
			update="[success:'message', failure:'message']"
			params="[id:itemType.id]"
			before="if(! confirm('${message(code:'itemType.delete.confirm')}')){return false}"
			onSuccess="\$('#edit_${itemType.id}').hide();">
		[<g:message code="delete"/>]
	</g:remoteLink>
</td>
