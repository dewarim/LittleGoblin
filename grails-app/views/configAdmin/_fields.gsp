<input type="hidden" name="id" value="${configEntry?.id}"/>
<label><g:message code="configEntry.name"/> </label><br>
<g:textField name="name" size="64" value="${configEntry?.name}"/>
<br>
<label><g:message code="configEntry.description"/> </label><br>
<g:textArea name="description" rows="4" cols="60" value="${configEntry?.description}"/>
<br>
<label><g:message code="configEntry.entryValue"/> </label><br>
<g:textArea name="entryValue" rows="4" cols="60" value="${configEntry?.entryValue}"/>
<br>