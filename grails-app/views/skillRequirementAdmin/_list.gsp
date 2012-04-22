<table class="requirement_list" border="1" cellpadding="2">
	<thead>
	<tr>
		<th><g:message code="requirement.level"/></th>
		<th><g:message code="requirement.skill"/></th>
		<th><g:message code="options"/></th>
	</tr>
	</thead>
	<tbody>
	<g:each in="${product.requiredSkills?.sort{it.skill.id}}" var="requirement">
		<tr id="requirement_${requirement.id}">
			<td>${requirement.level}</td>
			<td>
				<g:message code="${requirement.skill.name}"/>
			</td>
			<td class="requirement_options">
					<g:remoteLink
			controller="skillRequirementAdmin" action="edit"
			update="[success:'requirement_'+requirement.id, failure:'message']"
			onSuccess="\$('#message').text('');"
			params="[id:requirement.id]"
					title="${message(code:'edit')}"
					>
			<g:message code="edit"/>
			</g:remoteLink>
				   &nbsp;
				<g:remoteLink controller="skillRequirementAdmin" action="delete"
						update="[success:'requirementList', failure:'message']"
						params="[id:requirement.id]"
						onSuccess="\$('#message').text('');"
					title="${message(code:'delete')}"
				><g:message code="delete"/></g:remoteLink>
			</td>
		</tr>
	</g:each>
	</tbody>
</table>