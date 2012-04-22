		<input type="hidden" name="id" value="${dice?.id}"/>
<label><g:message code="dice.name"/> </label><br>
<g:textField name="name" value="${dice?.name}"/>
<br>
<label><g:message code="dice.amount"/> </label><br>
<g:textField name="amount" size="12" value="${dice?.amount ?: 0}"/>
<br>
<label><g:message code="dice.sides"/> </label><br>
<g:textField name="sides" size="12" value="${dice?.sides ?: 1}"/>
<br>
<label><g:message code="dice.bonus"/> </label><br>
<g:textField name="bonus" size="12" value="${dice?.bonus ?: 0}"/>
<br>
