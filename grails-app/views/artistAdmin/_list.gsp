<g:if test="${artists?.size() > 0}">

<table class="adminTable">
	<thead>
	<tr>
		<th><g:message code="artist.name"/></th>
		<th><g:message code="artist.website"/></th>
		<th><g:message code="artist.images"/> </th>
		<th><g:message code="artist.options"/></th>
	</tr>
	</thead>
	<tbody>
	<g:each in="${artists}" var="artist">
		<tr id="edit_${artist.id}" class="editable_table">
			<g:render template="update" model="[artist:artist]"/>
		</tr>
	</g:each>
	</tbody>
</table>
	</g:if>
<g:else>
	<g:message code="artist.admin.none.defined"/>
</g:else>