<td colspan="5">
	<g:form>
		<g:render template="fields" model="[product:product]"/>
		<g:submitToRemote
				url="[controller:'productAdmin', action:'update']"
				update="[success:'productList', failure:'message']"
				value="${message(code:'update')}"
			    onSuccess="\$('#message').text('');"
		/>
		<br>
		<div class="cancel_edit">
		<p>
		<g:remoteLink
					controller="productAdmin" action="cancelEdit"
					update="[success:'edit_'+product.id, failure:'message']"
					params="[id:product.id]"
					onSuccess="\$('#message').text('');">
				[<g:message code="admin.cancel.edit"/>]
			</g:remoteLink>
			</p>
		</div>
	</g:form>
</td>
