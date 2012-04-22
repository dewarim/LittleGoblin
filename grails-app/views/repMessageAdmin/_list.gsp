<table class="rep_message_list" border="1" cellpadding="2">
	<thead>
	<tr>
		<th><g:message code="repMessage.reputation"/></th>
		<th><g:message code="repMessage.id.trans"/></th>
		<th><g:message code="repMessage.options"/></th>
	</tr>
	</thead>
	<tbody>
	<g:each in="${rmm.repMessages?.sort{it.reputation}?.reverse()}" var="rm">
		<tr id="repMessage_${rm.id}">
			<td>${rm.reputation}</td>
			<td>
				${rm.messageId}<br>
				<em><g:message code="${rm.messageId}"/></em>
			</td>
			<td class="repMessage_options">
					<g:remoteLink
			controller="repMessageAdmin" action="edit"
			update="[success:'repMessage_'+rm.id, failure:'message']"
			onSuccess="\$('#message').text('');"
			params="[id:rm.id]"
					title="${message(code:'edit')}"
					>
			<g:message code="edit"/>
			</g:remoteLink>
				   &nbsp;
				<g:remoteLink controller="repMessageAdmin" action="delete"
						update="[success:'repMessageList', failure:'message']"
						params="[id:rm.id]"
						onSuccess="\$('#message').text('');"
					title="${message(code:'delete')}"
				><g:message code="delete"/></g:remoteLink>
			</td>
		</tr>
	</g:each>
	</tbody>
</table>