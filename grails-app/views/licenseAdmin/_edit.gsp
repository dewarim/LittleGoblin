<td colspan="5">
	<g:form>
		<g:render template="fields" model="[license:license]"/>
		<g:submitToRemote
				url="[controller:'licenseAdmin', action:'update']"
				update="[success:'licenseList', failure:'message']"
				value="${message(code:'update')}"
			    onSuccess="\$('#message').text('');"
		/>
		<br>
		<div class="cancel_edit">
		<p>
		<g:remoteLink
					controller="licenseAdmin" action="cancelEdit"
					update="[success:'edit_'+license.id, failure:'message']"
					params="[id:license.id]"
					onSuccess="\$('#message').text('');">
				[<g:message code="admin.cancel.edit"/>]
			</g:remoteLink>
			</p>
		</div>
	</g:form>
</td>
