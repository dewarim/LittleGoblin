<td colspan="5">
	<g:form>
		<g:render template="fields" model="[faction:faction, rmmList:rmmList]"/>
		<g:submitToRemote
				url="[controller:'factionAdmin', action:'update']"
				update="[success:'edit_'+faction.id, failure:'message']"
				value="${message(code:'update')}"
			    onSuccess="\$('#message').text('');"
		/>
		<br>
		<div class="cancel_edit">
		<p>
		<g:remoteLink
					controller="factionAdmin" action="cancelEdit"
					update="[success:'edit_'+faction.id, failure:'message']"
					params="[id:faction.id]"
					onSuccess="\$('#message').text('');">
				[<g:message code="admin.cancel.edit"/>]
			</g:remoteLink>
			</p>
		</div>
	</g:form>
</td>
