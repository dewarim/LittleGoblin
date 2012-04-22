<input type="hidden" name="id" value="${faction?.id}"/>
<label><g:message code="faction.name"/> </label><br>
<g:textField name="name" value="${faction?.name}"/>
<br>
<label><g:message code="faction.description"/> </label><br>
<g:textArea cols="60" rows="4" name="description" value="${faction?.description ?: 0}"/>
<br>
<label><g:message code="faction.start_level"/> </label><br>
<g:textField name="start_level" size="12" value="${faction?.startLevel ?: 1}"/>
<br>
<g:if test="${rmmList}">
	<label><g:message code="faction.repMessageMap"/> </label><br>
	<g:select from="${rmmList}" name="repMessageMap" optionKey="id" optionValue="name"/>
	<br>
</g:if>

