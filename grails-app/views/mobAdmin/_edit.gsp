<td colspan="5">
	<g:form>
		<g:render template="fields" model="[mob:mob, imageList:imageList]"/>
		<g:submitToRemote
				url="[controller:'mobAdmin', action:'update']"
				update="[success:'mobList', failure:'message']"
				value="${message(code:'update')}"
			    onSuccess="\$('#message').text('');"
		/>
		<br>
		<div class="cancel_edit">
		<p>
		<g:remoteLink
					controller="mobAdmin" action="cancelEdit"
					update="[success:'edit_'+mob.id, failure:'message']"
					params="[id:mob.id]"
					onSuccess="\$('#message').text('');">
				[<g:message code="admin.cancel.edit"/>]
			</g:remoteLink>
			</p>
		</div>
	</g:form>
</td>
