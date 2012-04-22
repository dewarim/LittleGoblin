<table class="component_list" border="1" cellpadding="2">
	<thead>
	<tr>
		<th><g:message code="component.type"/></th>
		<th><g:message code="component.amount"/></th>
		<th><g:message code="component.itemType"/></th>
		<th><g:message code="options"/></th>
	</tr>
	</thead>
	<tbody>
	<g:each in="${product.components?.sort{it.id}}" var="component">
		<tr id="component_${component.id}">
			<td>${component.type}</td>
			<td>${component.amount}</td>
			<td>
				<g:message code="${component.itemType.name}"/>
			</td>
			<td class="component_options">
					<g:remoteLink
			controller="componentAdmin" action="edit"
			update="[success:'component_'+component.id, failure:'message']"
			onSuccess="\$('#message').text('');"
			params="[id:component.id]"
					title="${message(code:'edit')}"
					>
			<g:message code="edit"/>
			</g:remoteLink>
				   &nbsp;
				<g:remoteLink controller="componentAdmin" action="delete"
						update="[success:'componentList', failure:'message']"
						params="[id:component.id]"
						onSuccess="\$('#message').text('');"
					title="${message(code:'delete')}"
				><g:message code="delete"/></g:remoteLink>
			</td>
		</tr>
	</g:each>
	</tbody>
</table>