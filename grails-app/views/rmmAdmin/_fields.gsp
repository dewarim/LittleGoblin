<input type="hidden" name="id" value="${rmm?.id}"/>
<label><g:message code="rmm.name"/></label><br>
<g:textField name="name" value="${rmm?.name}"/>
<br>
<label><g:message code="rmm.faction"/></label><br>
<g:select from="${factionList}" name="faction" optionKey="id" optionValue="name"
		noSelection="${['null':'---']}" value="${rmm?.faction?.id}"/>
<br>
