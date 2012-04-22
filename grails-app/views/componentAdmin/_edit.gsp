<td colspan="5">
	<g:form>
		<g:render template="fields" model="[product:product, component:component]"/>
		<g:submitToRemote
				url="[controller:'componentAdmin', action:'update']"
				update="[success:'componentList', failure:'message']"
				value="${message(code:'update')}"
			    onSuccess="\$('#message').text('');"
		/>
		<br>
		<div class="cancel_edit">
		<p>
		<g:remoteLink
					controller="componentAdmin" action="cancelEdit"
					update="[success:'componentList', failure:'message']"
					params="[id:component.id]"
					onSuccess="\$('#message').text('');">
				[<g:message code="admin.cancel.edit"/>]
			</g:remoteLink>
			</p>
		</div>
	</g:form>
</td>
