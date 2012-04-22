<td colspan="4">
	<g:form>
		<g:render template="fields" model="[category:category, selectedShops:selectedShops, selectedItemTypes:selectedItemTypes]"/>
		<g:submitToRemote
				url="[controller:'categoryAdmin', action:'update']"
				update="[success:'edit_'+category.id, failure:'message']"
				value="${message(code:'update')}"
			    onSuccess="\$('#message').text('');"
		/>
		<br>
		<div class="cancel_edit">
		<p>
		<g:remoteLink
					controller="categoryAdmin" action="cancelEdit"
					update="[success:'edit_'+category.id, failure:'message']"
					params="[id:category.id]"
					onSuccess="\$('#message').text('');">
				[<g:message code="admin.cancel.edit"/>]
			</g:remoteLink>
			</p>
		</div>
	</g:form>
</td>
