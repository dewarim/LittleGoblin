<g:if test="${guilds?.size() > 0}">

<table class="adminTable">
	<thead>
	<tr>
		<th><g:message code="guild.name"/></th>
		<th><g:message code="guild.description"/></th>
		<th><g:message code="guild.entryFee"/></th>
		<th><g:message code="guild.incomeTax"/></th>
		<th><g:message code="guild.academies"/></th>
		<th><g:message code="guild.options"/></th>
	</tr>
	</thead>
	<tbody>
	<g:each in="${guilds}" var="guild">
		<tr id="edit_${guild.id}" class="editable_table">
			<g:render template="update" model="[guild:guild]"/>
		</tr>
	</g:each>
	</tbody>
</table>
	</g:if>
<g:else>
	<g:message code="guild.admin.none.defined"/>
</g:else>