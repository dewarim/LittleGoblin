<td colspan="4">
	<g:form>
		<g:render template="fields" model="[shopOwner:shopOwner]"/>
		<g:submitToRemote
				url="[controller:'shopOwnerAdmin', action:'update']"
				update="[success:'shopOwnerList', failure:'message']"
				value="${message(code:'update')}"
			    onSuccess="\$('#message').text('');"
		/>
		<br>
		<div class="cancel_edit">
		<p>
		<g:remoteLink
					controller="shopOwnerAdmin" action="cancelEdit"
					update="[success:'edit_'+shopOwner.id, failure:'message']"
					params="[id:shopOwner.id]"
					onSuccess="\$('#message').text('');">
				[<g:message code="admin.cancel.edit"/>]
			</g:remoteLink>
			</p>
		</div>
	</g:form>
</td>
