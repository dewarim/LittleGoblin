<td colspan="5">
	<g:form>
		<g:render template="fields" model="[shop:shop]"/>
		<g:submitToRemote
				url="[controller:'shopAdmin', action:'update']"
				update="[success:'shopList', failure:'message']"
				value="${message(code:'update')}"
			    onSuccess="\$('#message').text('');"
		/>
		<br>
		<div class="cancel_edit">
		<p>
		<g:remoteLink
					controller="shopAdmin" action="cancelEdit"
					update="[success:'edit_'+shop.id, failure:'message']"
					params="[id:shop.id]"
					onSuccess="\$('#message').text('');">
				[<g:message code="admin.cancel.edit"/>]
			</g:remoteLink>
			</p>
		</div>
	</g:form>
</td>
