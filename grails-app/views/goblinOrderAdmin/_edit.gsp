<%@ page import="de.dewarim.goblin.goblinOrder.goblinOrder; de.dewarim.goblin.town.Town" %>
<td colspan="4">
	<g:form>
		<g:render template="fields" model="[goblinOrder:goblinOrder]"/>
		<g:submitToRemote
				url="[controller:'goblinOrderAdmin', action:'update']"
				update="[success:'edit_'+goblinOrder.id, failure:'message']"
				value="${message(code:'update')}"
			    onSuccess="\$('#message').text('');"
		/>
		<br>
		<div class="cancel_edit">
		<p>
		<g:remoteLink
					controller="goblinOrderAdmin" action="cancelEdit"
					update="[success:'edit_'+goblinOrder.id, failure:'message']"
					params="[id:goblinOrder.id]"
					onSuccess="\$('#message').text('');">
				[<g:message code="admin.cancel.edit"/>]
			</g:remoteLink>
			</p>
		</div>
	</g:form>
</td>
