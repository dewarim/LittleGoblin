<td colspan="5">
	<g:form>
		<g:render template="fields" model="[rmm:rmm, repMessage:repMessage]"/>
		<g:submitToRemote
				url="[controller:'repMessageAdmin', action:'update']"
				update="[success:'repMessageList', failure:'message']"
				value="${message(code:'update')}"
			    onSuccess="\$('#message').text('');"
		/>
		<br>
		<div class="cancel_edit">
		<p>
		<g:remoteLink
					controller="repMessageAdmin" action="cancelEdit"
					update="[success:'repMessageList', failure:'message']"
					params="[id:repMessage.id]"
					onSuccess="\$('#message').text('');">
				[<g:message code="admin.cancel.edit"/>]
			</g:remoteLink>
			</p>
		</div>
	</g:form>
</td>
