<td colspan="5">
	<g:form>
		<g:render template="fields" model="[rmm:rmm, factionList:factionList]"/>
		<g:submitToRemote
				url="[controller:'rmmAdmin', action:'update']"
				update="[success:'rmmList', failure:'message']"
				value="${message(code:'update')}"
			    onSuccess="\$('#message').text('');"
		/>
		<br>
		<div class="cancel_edit">
		<p>
		<g:remoteLink
					controller="rmmAdmin" action="cancelEdit"
					update="[success:'edit_'+rmm.id, failure:'message']"
					params="[id:rmm.id]"
					onSuccess="\$('#message').text('');">
				[<g:message code="admin.cancel.edit"/>]
			</g:remoteLink>
			</p>
		</div>
	</g:form>
</td>
