<div id="inner_mailbox">
	<g:form name="mailbox">
		<select id="boxId"
				name="box"
				onchange="${remoteFunction(action:'showBox', controller:'mailBox', update:[success:'inner_mailbox', failure:'message'], params:'\'box=\'+document.getElementById(\'boxId\').value')}">
			<g:each in="${pc.mailBoxes.sort{it.equals(mailBox)}.reverse()}" var="box">
				<option value="${box.id}">${message(code:box.boxType.name)}</option>
			</g:each>
		</select>

	</g:form>
	<g:if test="${mails.isEmpty()}">
		<g:message code="mail.box.empty"/>
	</g:if>
	<g:else>
		<div id="message_list">
			<g:render template="/mailBox/list_mails" model="['mails':mails, 'max':max, 'offset':offset, 'mailBox':mailBox]"/>
		</div>
	</g:else>
	<div id="mail_content" class="mail_content">
		&nbsp;
	</div>
	
</div>