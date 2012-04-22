<td colspan="10">
	<g:form>
		<g:render template="fields"
				model="[questStep:questStep, parentSteps:parentSteps, childSteps:childSteps, newSteps:newSteps]"/>
		<g:submitToRemote
				url="[controller:'questStepAdmin', action:'update']"
				update="[success:'questStepList', failure:'message']"
				value="${message(code:'update')}"
			    onSuccess="\$('#message').text('');"
		/>
		<br>
		<div class="cancel_edit">
		<p>
		<g:remoteLink
					controller="questStepAdmin" action="cancelEdit"
					update="[success:'edit_'+questStep.id, failure:'message']"
					params="[id:questStep.id]"
					onSuccess="\$('#message').text('');">
				[<g:message code="admin.cancel.edit"/>]
			</g:remoteLink>
			</p>
		</div>
	</g:form>
</td>
