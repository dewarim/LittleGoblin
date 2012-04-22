<g:if test="${features?.size() > 0}">

<table class="adminTable">
	<thead>
	<tr>
		<th><g:message code="feature.name"/></th>
		<th><g:message code="feature.internalName"/></th>
		<th><g:message code="feature.script"/></th>
		<th><g:message code="feature.options"/></th>
	</tr>
	</thead>
	<tbody>
	<g:each in="${features}" var="feature">
		<tr id="edit_${feature.id}" class="editable_table">
			<g:render template="row" model="[feature:feature]"/>
		</tr>
	</g:each>
	</tbody>
</table>
	</g:if>
<g:else>
	<g:message code="feature.admin.none.defined"/>
</g:else>