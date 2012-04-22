<td colspan="4">
	<g:form>
		<g:render template="fields" model="[itemFeature:itemFeature]"/>
		<g:submitToRemote
				url="[controller:'itemFeatureAdmin', action:'update']"
				update="[success:'edit_'+itemFeature.id, failure:'message']"
				value="${message(code:'update')}"
			    onSuccess="\$('#message').text('');"
		/>
		<br>
		<div class="cancel_edit">
		<p>
		<g:remoteLink
					controller="itemFeatureAdmin" action="cancelEdit"
					update="[success:'edit_'+itemFeature.id, failure:'message']"
					params="[id:itemFeature.id]"
					onSuccess="\$('#message').text('');">
				[<g:message code="admin.cancel.edit"/>]
			</g:remoteLink>
			</p>
		</div>
	</g:form>
</td>
