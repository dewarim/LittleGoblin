<td>
	${questStep.name}<br>
	(<g:message code="${questStep.name}"/>)
</td>
<td>
	${questStep.title}<br>
	(<g:message code="${questStep.title}"/>)
</td>
<td>
	<p>${questStep.description}</p>
	<hr>
	<p><em><g:message code="desc.translation"/></em>:</p>
	<p class="questStep_description"><g:message code="${questStep.description}"/></p>
</td>
<td>
	<g:if test="${questStep.intro}">
		<p>${questStep.intro}</p>
		<hr>
		<p><em><g:message code="desc.translation"/></em>:</p>
		<p class="questStep_intro"><g:message code="${questStep.intro}"/></p>
	</g:if>
</td>
<td>
	<g:message code="${questStep.endOfQuest ? 'default.boolean.true' : 'default.boolean.false'}"/>
</td>
<td>
	<g:message code="${questStep.questTemplate.name}"/>
</td>
<td>
	<g:message code="${questStep.encounter.name}"/>
</td>
<td>
	<ul class="parent_steps">
		<g:each in="${questStep.parentSteps}" var="step">
			<li><g:message code="${step.parent.name}"/></li>
		</g:each>
	</ul>
</td>
<td>
	<ul class="next_steps">
		<g:each in="${questStep.nextSteps}" var="step">
			<li><g:message code="${step.child.name}"/></li>
		</g:each>
	</ul>
</td>

<td>
	<g:remoteLink
			controller="questStepAdmin" action="edit"
			update="[success:'edit_'+questStep.id, failure:'message']"
			onSuccess="\$('#message').text('');"
			params="[id:questStep.id]">
		[<g:message code="edit"/>]
	</g:remoteLink>
	&nbsp;
	<g:remoteLink
			controller="questStepAdmin" action="delete"
			update="[success:'edit_'+questStep.id, failure:'message']"
			onSuccess="\$('#edit_${questStep.id}').hide();"
			params="[id:questStep.id]">
		[<g:message code="delete"/>]
	</g:remoteLink>

</td>
