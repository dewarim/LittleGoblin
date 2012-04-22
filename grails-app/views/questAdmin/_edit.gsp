<td colspan="5">
	<g:form>
		<g:render template="fields" model="[questTemplate:questTemplate]"/>
		<g:submitToRemote
				url="[controller:'questAdmin', action:'update']"
				update="[success:'questTemplateList', failure:'message']"
				value="${message(code:'update')}"
			    onSuccess="\$('#message').text('');"
		/>
		<br>
		<div class="cancel_edit">
		<p>
		<g:remoteLink
					controller="questAdmin" action="cancelEdit"
					update="[success:'edit_'+questTemplate.id, failure:'message']"
					params="[id:questTemplate.id]"
					onSuccess="\$('#message').text('');">
				[<g:message code="admin.cancel.edit"/>]
			</g:remoteLink>
			</p>
		</div>
	</g:form>
</td>
