<%@ page import="de.dewarim.goblin.guild.Guild; de.dewarim.goblin.town.Town" %>
<td colspan="4">
	<g:form>
		<g:render template="fields" model="[guild:guild]"/>
		<g:submitToRemote
				url="[controller:'guildAdmin', action:'update']"
				update="[success:'edit_'+guild.id, failure:'message']"
				value="${message(code:'update')}"
			    onSuccess="\$('#message').text('');"
		/>
		<br>
		<div class="cancel_edit">
		<p>
		<g:remoteLink
					controller="guildAdmin" action="cancelEdit"
					update="[success:'edit_'+guild.id, failure:'message']"
					params="[id:guild.id]"
					onSuccess="\$('#message').text('');">
				[<g:message code="admin.cancel.edit"/>]
			</g:remoteLink>
			</p>
		</div>
	</g:form>
</td>
