<td colspan="5">
	<g:form>
		<g:render template="fields" model="[questGiver:questGiver]"/>
		<g:submitToRemote
				url="[controller:'questGiverAdmin', action:'update']"
				update="[success:'questGiverList', failure:'message']"
				value="${message(code:'update')}"
			    onSuccess="\$('#message').text('');"
		/>
		<br>
		<div class="cancel_edit">
		<p>
		<g:remoteLink
					controller="questGiverAdmin" action="cancelEdit"
					update="[success:'edit_'+questGiver.id, failure:'message']"
					params="[id:questGiver.id]"
					onSuccess="\$('#message').text('');">
				[<g:message code="admin.cancel.edit"/>]
			</g:remoteLink>
			</p>
		</div>
	</g:form>
</td>
