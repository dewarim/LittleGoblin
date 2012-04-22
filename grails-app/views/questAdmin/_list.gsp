<g:if test="${questTemplates?.size() > 0}">

<table class="adminTable">
	<thead>
	<tr>
		<th><g:message code="name"/></th>
		<th><g:message code="description"/></th>
		<th><g:message code="quest.template.level"/></th>
		<th><g:message code="quest.template.active"/></th>
		<th><g:message code="quest.template.steps"/></th>
		<th><g:message code="quest.template.requirements"/></th>
		<th><g:message code="quest.template.giver"/></th>
		<th><g:message code="options"/></th>
	</tr>
	</thead>
	<tbody>
	<g:each in="${questTemplates}" var="questTemplate">
		<tr id="edit_${questTemplate.id}" class="editable_table">
			<g:render template="update" model="[questTemplate:questTemplate]"/>
		</tr>
	</g:each>
	</tbody>
</table>
	</g:if>
<g:else>
	<g:message code="questTemplate.admin.none.defined"/>
</g:else>