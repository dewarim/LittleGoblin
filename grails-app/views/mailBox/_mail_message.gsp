<table class="display_mail">
	<tr>
		<td><g:message code="mail.from"/> </td>
		<td>${mail.sender.name}</td>
	</tr>
	<tr>
		<td><g:message code="mail.subject"/> </td>
		<td>${mail.subject}</td>
	</tr>
	<tr>
		<td valign="top"><g:message code="mail.content"/> </td>
		<td  class="message_content">
			${mail.content}
		</td>
	</tr>
	<tr>
		<td valign="top"><g:message code="mail.reply"/> </td>
		<td>
			<div id="reply_form">
				<g:form>
					<input type="hidden" name="recipient" value="${mail.recipient.id}">
					<g:message code="mail.subject"/>:
					<g:textField name="replySubject" value="${message(code:'mail.reply.re')} ${mail.subject}"/><br>
					<g:textArea name="replyContent" value="" rows="10" cols="60"/><br>
					<g:submitToRemote url="[action:'sendMessage', controller:'mailBox']"
						update="[success:'reply_form', failure:'message']"
						value="${message(code:'mail.reply.submit')}"/>
				</g:form>
			</div>
		</td>
	</tr>
</table>
