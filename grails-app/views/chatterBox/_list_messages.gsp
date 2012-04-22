<div id="chat_messages" class="chat_messages">
	<g:each in="${chatterBox.chatMessages.sort{it.sent}.reverse()}" var="msg">
		<p><strong>${msg.sender.name}</strong> (	<g:formatDate format="yyyy-MM-dd hh:mm:ss" date="${msg.sent}"/>)<br>
		   ${msg.content}
		</p>
	</g:each>
</div>