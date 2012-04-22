<td>
	${questTemplate.name}
</td>
<td>
	<g:if test="${questTemplate.description}">
		<p>${questTemplate.description}</p>
		<hr>
		<p><em><g:message code="desc.translation"/></em>:</p>
		<p class="questTemplate_description"><g:message code="${questTemplate.description}"/></p>
	</g:if>

</td>
<td class="center">
	${questTemplate.level}
</td>
<td class="center">
	<g:if test="${questTemplate.active}">
		<g:message code="active"/>
	</g:if>
	<g:else>
		<g:message code="inactive"/>
	</g:else>
</td>
<td>
	<ul>
		<g:each in="${questTemplate.steps}" var="step">
			<li>${step.name}</li>
		</g:each>
	</ul>
</td>
<td>
	<g:message code="todo.implement.me"/>
</td>
<td>
	<g:message code="${questTemplate.giver.name}"/>
</td>
<td>
	<g:remoteLink
			controller="questAdmin" action="edit"
			update="[success:'edit_'+questTemplate.id, failure:'message']"
			onSuccess="\$('#message').text('');"
			params="[id:questTemplate.id]">
		[<g:message code="edit"/>]
	</g:remoteLink>
	&nbsp;
	<g:remoteLink
			controller="questAdmin" action="delete"
			update="[success:'message', failure:'message']"
			params="[id:questTemplate.id]"
			before="if(! confirm('${message(code:'questTemplate.delete.confirm')}')){return false}"
			onSuccess="\$('#edit_${questTemplate.id}').hide();">
		[<g:message code="delete"/>]
	</g:remoteLink>
</td>
