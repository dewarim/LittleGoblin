<%@ page import="de.dewarim.goblin.town.Town;" %>
<td colspan="4">
	<g:form>
		<g:render template="fields" model="[town:town]"/>
		<g:submitToRemote
				url="[controller:'townAdmin', action:'update']"
				update="[success:'edit_'+town.id, failure:'message']"
				value="${message(code:'update')}"
			    onSuccess="\$('#message').text('');"
		/>
		<br>
		<div class="cancel_edit">
		<p>
		<g:remoteLink
					controller="townAdmin" action="cancelEdit"
					update="[success:'edit_'+town.id, failure:'message']"
					params="[id:town.id]"
					onSuccess="\$('#message').text('');">
				[<g:message code="admin.cancel.edit"/>]
			</g:remoteLink>
			</p>
		</div>
	</g:form>
</td>
