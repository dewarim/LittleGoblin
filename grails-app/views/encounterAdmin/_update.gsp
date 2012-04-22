<td>
	${encounter.name}
</td>
<td>
	<g:message code="${encounter.includesCombat ? 'default.boolean.true' : 'default.boolean.false'}"/>
</td>
<td>
	<g:if test="${encounter.script}">
		<g:message code="${encounter.script.name}"/>
	</g:if>
	<g:else>
		---
	</g:else>
</td>
<td>
	${encounter.config?.encodeAsHTML()}
</td>
<td class="mob_list">
	<ul>
		<g:each in="${encounter.mobs}" var="encounterMob">
			<li><g:message code="${encounterMob.mob.name}"/></li>
		</g:each>
	</ul>
</td>
<td class="steps_list">
	<ul>
		<g:each in="${encounter.steps}" var="step">
			<li><g:message code="${step.name}"/></li>
		</g:each>
	</ul>
</td>
<td>
	<g:remoteLink
			controller="encounterAdmin" action="edit"
			update="[success:'edit_'+encounter.id, failure:'message']"
			onSuccess="\$('#message').text('');"
			params="[id:encounter.id]">
		[<g:message code="edit"/>]
	</g:remoteLink>
	&nbsp;
	<g:remoteLink
			controller="encounterAdmin" action="delete"
			update="[success:'message', failure:'message']"
			params="[id:encounter.id]"
			onSuccess="\$('#edit_${encounter.id}').hide();">
		[<g:message code="delete"/>]
	</g:remoteLink>
</td>
