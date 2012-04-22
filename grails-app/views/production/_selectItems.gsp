<g:form action="startProduction" controller="production" name="startProduction">
	<div id="selectItemMessage"></div>
	<g:if test="${prodStarted}"><div id="startProductionMessage">${prodStarted}</div></g:if>
	<input type="hidden" name="product" value="${product.id}">
	<table border="1" cellpadding="6">

		<thead>
			<tr>
				<th><g:message code="product.amount"/> </th>
				<th><g:message code="product.component"/> </th>
			</tr>
		</thead>
		<tbody>
			<g:each in="${product.fetchInputItems()}" var="component">
				<tr>
					<td>${component.amount}</td>
					<td><g:message code="product.component"/>: <g:message code="${component.itemType.name}"/></td>
				</tr>
				<g:each in="${itemMap.get(component)}" var="item">
					<tr>
						<td>
							<input type="text" maxlength="4" name="item_${item.id}" value="${selectedItems?.get(item) ?: 0}">
						</td>
						<td>
							<g:message code="${item.type.name}"/>
						</td>
					</tr>
				</g:each>
			</g:each>
		</tbody>
	</table>

	<g:submitToRemote update="[success:'selectItems', failure:'selectItemMessage']"
		url="[action:'startProduction', controller:'production']"
		value="${message(code:'product.start.button')}"
	/>
</g:form>
<g:if test="${jobCount}">
	<g:link action="listProductionJobs" controller="production">
		<g:message code="link.to.productionJobs"/>
	</g:link>
</g:if>