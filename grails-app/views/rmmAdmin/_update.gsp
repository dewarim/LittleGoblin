<%@ page import="de.dewarim.goblin.reputation.Faction" %>
<td>
	${rmm.name}
</td>

<td class="where_used_list">
	<g:message code="${rmm.faction?.name}"/>
</td>
<td class="where_used_list">
	<ul>
		<g:each in="${rmm.repMessages?.sort{it.reputation}?.reverse()}" var="rm">
			<li>
				${rm.messageId}<br>
				<g:message code="${rm.messageId}"/>
			</li>
		</g:each>
	</ul>
</td>
<td>
	<g:remoteLink
			controller="rmmAdmin" action="edit"
			update="[success:'edit_'+rmm.id, failure:'message']"
			onSuccess="\$('#message').text('');"
			params="[id:rmm.id]">
		[<g:message code="edit"/>]
	</g:remoteLink>
	&nbsp;
	<g:remoteLink
			controller="rmmAdmin" action="delete"
			update="[success:'message', failure:'message']"
			params="[id:rmm.id]"
			onSuccess="\$('#edit_${rmm.id}').hide();">
		[<g:message code="delete"/>]
	</g:remoteLink>
	<br>
	<g:link controller="repMessageAdmin" action="index"
			params="[id:rmm.id]">
		<g:message code="rmm.edit.messages"/>
	</g:link>
</td>
