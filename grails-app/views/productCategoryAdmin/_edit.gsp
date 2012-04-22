<td colspan="5">
	<g:form>
		<g:render template="fields" model="[category:category]"/>
		<g:submitToRemote
				url="[controller:'productCategoryAdmin', action:'update']"
				update="[success:'categoryList', failure:'message']"
				value="${message(code:'update')}"
			    onSuccess="\$('#message').text('');"
		/>
		<br>
		<div class="cancel_edit">
		<p>
		<g:remoteLink
					controller="productCategoryAdmin" action="cancelEdit"
					update="[success:'edit_'+category.id, failure:'message']"
					params="[id:category.id]"
					onSuccess="\$('#message').text('');">
				[<g:message code="admin.cancel.edit"/>]
			</g:remoteLink>
			</p>
		</div>
	</g:form>
</td>
