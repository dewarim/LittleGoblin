<td colspan="7">
	<g:form>
		<g:render template="fields" model="[image:image]"/>
		<g:submitToRemote
				url="[controller:'imageAdmin', action:'update']"
				update="[success:'imageList', failure:'message']"
				value="${message(code:'update')}"
			    onSuccess="\$('#message').text('');"
		/>
		<br>
		<div class="cancel_edit">
		<p>
		<g:remoteLink
					controller="imageAdmin" action="cancelEdit"
					update="[success:'edit_'+image.id, failure:'message']"
					params="[id:image.id]"
					onSuccess="\$('#message').text('');">
				[<g:message code="admin.cancel.edit"/>]
			</g:remoteLink>
			</p>
		</div>
	</g:form>
</td>
