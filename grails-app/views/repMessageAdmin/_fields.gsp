<input type="hidden" name="rmm" value="${rmm.id}">
<input type="hidden" name="id" value="${repMessage?.id}">
<label for="reputation_${repMessage?.id}"><g:message code="repMessage.reputation"/></label>
<g:textField name="reputation" value="${repMessage?.reputation}" id="reputation_${repMessage?.id}"/>
<label for="msgRep_${repMessage?.id}"><g:message code="repMessage.id"/></label>
<g:textField name="messageId" value="${repMessage?.messageId}" id="msgRep_${repMessage?.id}"/>
