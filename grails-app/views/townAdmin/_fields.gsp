<%@ page import="de.dewarim.goblin.town.Academy" %>
		<input type="hidden" name="id" value="${town?.id}"/>
<label><g:message code="town.name"/> </label><br>
<g:textField name="name" value="${town?.name}"/>
<br>
<label><g:message code="town.description"/> </label><br>
<g:textArea name="description" rows="6" cols="60" value="${town?.description}"/>
<br>
<label><g:message code="town.shortDescription"/> </label><br>
<g:textArea name="shortDescription" rows="3" cols="60" value="${town?.shortDescription}"/>
<br>
