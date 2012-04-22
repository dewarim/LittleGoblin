<td colspan="5">
	<g:form>
		<g:render template="fields" model="[product:product, requirement:requirement]"/>
		<g:submitToRemote
				url="[controller:'skillRequirementAdmin', action:'update']"
				update="[success:'requirementList', failure:'message']"
				value="${message(code:'update')}"
			    onSuccess="\$('#message').text('');"
		/>
		<br>
		<div class="cancel_edit">
		<p>
		<g:remoteLink
					controller="skillRequirementAdmin" action="cancelEdit"
					update="[success:'requirementList', failure:'message']"
					params="[id:requirement.id]"
					onSuccess="\$('#message').text('');">
				[<g:message code="admin.cancel.edit"/>]
			</g:remoteLink>
			</p>
		</div>
	</g:form>
</td>
