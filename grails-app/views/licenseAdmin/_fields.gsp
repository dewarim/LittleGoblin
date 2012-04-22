<input type="hidden" name="id" value="${license?.id}"/>
<label><g:message code="license.name"/> </label><br>
<g:textField name="name" value="${license?.name}"/>
<br>
<label><g:message code="license.description"/> </label><br>
<g:textArea name="description" rows="6" cols="60" value="${license?.description}"/>
<br>
<label><g:message code="license.url"/> </label><br>
<g:textField name="url" value="${license?.url}"/>
