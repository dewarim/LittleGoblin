<g:if test="${images?.size() > 0}">

<table class="adminTable">
	<thead>
	<tr>
		<th><g:message code="image.name"/></th>
		<th><g:message code="image.artist"/></th>
		<th><g:message code="image.description"/></th>
		<th><g:message code="image.url"/></th>
		<th><g:message code="image.sourceUrl"/></th>
		<th><g:message code="image.uses"/> </th>
		<th><g:message code="image.options"/></th>
	</tr>
	</thead>
	<tbody>
	<g:each in="${images}" var="image">
		<tr id="edit_${image.id}" class="editable_table">
			<g:render template="update" model="[image:image]"/>
		</tr>
	</g:each>
	</tbody>
</table>
	</g:if>
<g:else>
	<g:message code="image.admin.none.defined"/>
</g:else>