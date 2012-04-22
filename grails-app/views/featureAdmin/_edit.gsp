<td colspan="4">
	<g:form>
		<g:render template="fields" model="[feature:feature]"/>
		<g:submitToRemote
				url="[controller:'featureAdmin', action:'update']"
				update="[success:'edit_'+feature.id, failure:'message']"
				value="${message(code:'update')}"
			    onSuccess="\$('#message').text('');"
		/>
		<br>
		<div class="cancel_edit">
		<p>
		<g:remoteLink
					controller="featureAdmin" action="cancelEdit"
					update="[success:'edit_'+feature.id, failure:'message']"
					params="[id:feature.id]"
					onSuccess="\$('#message').text('');">
				[<g:message code="admin.cancel.edit"/>]
			</g:remoteLink>
			</p>
		</div>
	</g:form>
</td>
