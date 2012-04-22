<td colspan="4">
	<g:form>
		<g:render template="fields" model="[artist:artist]"/>
		<g:submitToRemote
				url="[controller:'artistAdmin', action:'update']"
				update="[success:'edit_'+artist.id, failure:'message']"
				value="${message(code:'update')}"
			    onSuccess="\$('#message').text('');"
		/>
		<br>
		<div class="cancel_edit">
		<p>
		<g:remoteLink
					controller="artistAdmin" action="cancelEdit"
					update="[success:'edit_'+artist.id, failure:'message']"
					params="[id:artist.id]"
					onSuccess="\$('#message').text('');">
				[<g:message code="admin.cancel.edit"/>]
			</g:remoteLink>
			</p>
		</div>
	</g:form>
</td>
