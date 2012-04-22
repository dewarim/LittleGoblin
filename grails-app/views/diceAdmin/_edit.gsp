<td colspan="5">
	<g:form>
		<g:render template="fields" model="[dice:dice]"/>
		<g:submitToRemote
				url="[controller:'diceAdmin', action:'update']"
				update="[success:'edit_'+dice.id, failure:'message']"
				value="${message(code:'update')}"
			    onSuccess="\$('#message').text('');"
		/>
		<br>
		<div class="cancel_edit">
		<p>
		<g:remoteLink
					controller="diceAdmin" action="cancelEdit"
					update="[success:'edit_'+dice.id, failure:'message']"
					params="[id:dice.id]"
					onSuccess="\$('#message').text('');">
				[<g:message code="admin.cancel.edit"/>]
			</g:remoteLink>
			</p>
		</div>
	</g:form>
</td>
