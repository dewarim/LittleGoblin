<td colspan="4">
	<g:form>
		<g:render template="fields" model="[configEntry:configEntry]"/>
		<g:submitToRemote
				url="[controller:'configAdmin', action:'update']"
				update="[success:'edit_'+configEntry.id, failure:'message']"
				value="${message(code:'update')}"
			    onSuccess="\$('#message').text('');"
		/>
		<br>
		<div class="cancel_edit">
		<p>
		<g:remoteLink
					controller="configAdmin" action="cancelEdit"
					update="[success:'edit_'+configEntry.id, failure:'message']"
					params="[id:configEntry.id]"
					onSuccess="\$('#message').text('');">
				[<g:message code="admin.cancel.edit"/>]
			</g:remoteLink>
			</p>
		</div>
	</g:form>
</td>
