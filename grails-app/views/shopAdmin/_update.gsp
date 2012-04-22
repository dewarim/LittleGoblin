<td>
	${shop.name}
</td>
<td>
	<g:if test="${shop.description}">
		<p>${shop.description}</p>
		<hr>
		<p><em><g:message code="shop.desc.translation"/></em>:</p>
		<p class="shop_description"><g:message code="${shop.description}"/></p>
	</g:if>

</td>
<td>
	<g:message code="${shop.owner.name}"/>
</td>
<td>
	<g:message code="${shop.town.name}"/>
</td>
<td>
	<g:remoteLink
			controller="shopAdmin" action="edit"
			update="[success:'edit_'+shop.id, failure:'message']"
			onSuccess="\$('#message').text('');"
			params="[id:shop.id]">
		[<g:message code="edit"/>]
	</g:remoteLink>
	&nbsp;
	<g:remoteLink
			controller="shopAdmin" action="delete"
			update="[success:'message', failure:'message']"
			params="[id:shop.id]"
			onSuccess="\$('#edit_${shop.id}').hide();">
		[<g:message code="delete"/>]
	</g:remoteLink>
</td>
