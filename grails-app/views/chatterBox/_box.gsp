<div id="inner_chatterbox">
	<g:if test="${pc.goblinOrder.chatterBoxes?.size() > 1}">
		<!-- This order has multiple chatterBoxes -->
	<g:form name="chatterboxSelect">
		<label for="boxId"><g:message code="chatterbox.select.label"/> </label>
		<select id="boxId"
				name="box"
				onchange="${remoteFunction(action:'showChatterBox', controller:'chatterBox', update:[success:'inner_chatterbox', failure:'message'], params:'\'box=\'+document.getElementById(\'boxId\').value')}">
			<g:each in="${pc.goblinOrder.chatterBoxes.sort{it.equals(currentBox)}.reverse()}" var="box">
				<option value="${box.id}">${message(code:box.name)}</option>
			</g:each>
		</select>

	</g:form>
	</g:if>
	<g:else>
		<!-- This order has only one chatterbox -->
		<g:message code="${currentBox.name}"/>
	</g:else>
	<hr>
	<g:if test="${currentBox.chatMessages.isEmpty()}">
		<g:message code="chatterbox.empty"/>
	</g:if>
	<g:else>
		<div class="chat_message_list">
			<g:render template="/chatterBox/list_messages" model="[chatterBox:currentBox]"/>
		</div>
	</g:else>
	<div id="mail_content" class="mail_content">
		<g:form name="writeChatMessage">
			<input type="hidden" name="box" value="${currentBox.id}"/>
			<g:message code="chatterbox.your.message"/><br>
			<g:textArea name="chatMessage" rows="4" columns="60"/>
			<g:submitToRemote update="[success:'inner_chatterbox', failure:'message']"
				url="[action:'sendChatMessage', controller:'chatterBox']"
				value="${message(code:'chatterbox.send')}"/>
		</g:form>
	</div>

</div>