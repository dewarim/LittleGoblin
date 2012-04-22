<%@ page import="de.dewarim.goblin.guild.Guild; de.dewarim.goblin.town.Town" %>
<td colspan="4">
	<g:form>
		<g:render template="fields" model="[academy:academy]"/>
		<g:submitToRemote
				url="[controller:'academyAdmin', action:'update']"
				update="[success:'edit_'+academy.id, failure:'message']"
				value="${message(code:'update')}"/>
		<br>
		<div class="cancel_edit">
		<p>

		<g:remoteLink
					controller="academyAdmin" action="cancelEdit"
					update="[success:'edit_'+academy.id, failure:'message']"
					params="[id:academy.id]"
					onSuccess="\$('#message').text('');">
				[<g:message code="admin.cancel.edit"/>]
			</g:remoteLink>
			</p>
		</div>
	</g:form>
</td>
