<input type="hidden" name="id" value="${questGiver?.id}"/>
<label><g:message code="name"/> </label><br>
<g:textField name="name" value="${questGiver?.name}"/>
<br>
<label><g:message code="description"/> </label><br>
<g:textArea name="description" rows="6" cols="60" value="${questGiver?.description}"/>
<br>
