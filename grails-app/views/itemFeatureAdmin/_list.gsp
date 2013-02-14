<g:if test="${itemFeatures?.size() > 0}">

<table class="adminTable">
	<thead>
	<tr>
		<th><g:message code="itemFeature.feature"/></th>
		<th><g:message code="itemFeature.itemType"/></th>
		<th><g:message code="itemFeature.config"/></th>
		<th><g:message code="itemFeature.options"/></th>
	</tr>
	</thead>
	<tbody>
	<g:each in="${itemFeatures}" var="itemFeature">
		<tr id="edit_${itemFeature.id}" class="editable_table">
			<g:render template="row" model="[itemFeature:itemFeature]"/>
		</tr>
	</g:each>
	</tbody>
</table>
	</g:if>
<g:else>
	<g:message code="admin.none.defined"/>
</g:else>