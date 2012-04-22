<td colspan="5">
	<g:form>
		<g:render template="fields" model="[encounter:encounter, mobList:mobList]"/>
		<g:submitToRemote
				url="[controller:'encounterAdmin', action:'update']"
				update="[success:'encounterList', failure:'message']"
				value="${message(code:'update')}"
			    onSuccess="\$('#message').text('');"
		/>
		<br>
		<div class="cancel_edit">
		<p>
		<g:remoteLink
					controller="encounterAdmin" action="cancelEdit"
					update="[success:'edit_'+encounter.id, failure:'message']"
					params="[id:encounter.id]"
					onSuccess="\$('#message').text('');">
				[<g:message code="admin.cancel.edit"/>]
			</g:remoteLink>
			</p>
		</div>
	</g:form>
</td>
