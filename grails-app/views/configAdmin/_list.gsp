<g:if test="${configEntries?.size() > 0}">

<table class="adminTable">
	<thead>
	<tr>
		<th><g:message code="configEntry.name"/></th>
		<th><g:message code="configEntry.description"/></th>
		<th><g:message code="configEntry.entryValue"/> </th>
		<th><g:message code="configEntry.options"/></th>
	</tr>
	</thead>
	<tbody>
	<g:each in="${configEntries}" var="configEntry">
		<tr id="edit_${configEntry.id}" class="editable_table">
			<g:render template="update" model="[configEntry:configEntry]"/>
		</tr>
	</g:each>
	</tbody>
</table>
	</g:if>
<g:else>
	<g:message code="configEntry.admin.none.defined"/>
</g:else>