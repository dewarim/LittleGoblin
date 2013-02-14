<g:if test="${rmms?.size() > 0}">
	<table class="adminTable">
		<thead>
		<tr>
			<th><g:message code="rmm.name"/></th>
			<th><g:message code="rmm.faction"/></th>
			<th><g:message code="rmm.messages"/></th>
			<th><g:message code="rmm.options"/></th>
		</tr>
		</thead>
		<tbody>
		<g:each in="${rmms}" var="rmm">
			<tr id="edit_${rmm.id}" class="editable_table">
				<g:render template="update" model="[rmm:rmm]"/>
			</tr>
		</g:each>
		</tbody>
	</table>
</g:if>
<g:else>
	<g:message code="admin.none.defined"/>
</g:else>