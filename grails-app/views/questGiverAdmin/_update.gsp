<td>
	${questGiver.name}<br>
	(<g:message code="${questGiver.name}"/>)
</td>
<td>
	<g:if test="${questGiver.description}">
		<p>${questGiver.description}</p>
		<hr>
		<p><em><g:message code="desc.translation"/></em>:</p>
		<p class="questGiver_description"><g:message code="${questGiver.description}"/></p>
	</g:if>
</td>
<td>
	<ul>
		<g:each in="${questGiver.templates.sort{it.level}}" var="template">
			<li>
				<g:link controller="questAdmin" action="index" params="[id:template.id]">
					<g:message code="${template.name}"/>
				</g:link>
			</li>
		</g:each>
	</ul>
</td>
<td>
	<g:remoteLink
			controller="questGiverAdmin" action="edit"
			update="[success:'edit_'+questGiver.id, failure:'message']"
			onSuccess="\$('#message').text('');"
			params="[id:questGiver.id]">
		[<g:message code="edit"/>]
	</g:remoteLink>
	&nbsp;
		<g:remoteLink
			controller="questGiverAdmin" action="delete"
			update="[success:'edit_'+questGiver.id, failure:'message']"
			onSuccess="\$('#edit_${questGiver.id}').hide();"
			params="[id:questGiver.id]">
		[<g:message code="delete"/>]
	</g:remoteLink>
	<!-- alternative:
	<g:form>
		<input type="hidden" name="id" value="${questGiver.id}">
		<g:submitToRemote url="[controller:'questGiverAdmin', action:'delete']"
	     	update="[success:'message', failure:'message']"
			onSuccess="\$('#edit_${questGiver.id}').hide();"
			before="if(! confirm('${message(code:'questGiver.delete.confirm')}')){return false}"
			value="${message(code:'delete')}"
		/>
	</g:form>
	-->

</td>
