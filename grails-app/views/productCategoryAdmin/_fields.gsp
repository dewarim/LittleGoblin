<%@ page import="de.dewarim.goblin.pc.crafting.Product" %>
<input type="hidden" name="id" value="${category?.id}"/>
<label><g:message code="name"/> </label><br>
<g:textField name="name" value="${category?.name}"/>
<br>
<label><g:message code="description"/> </label><br>
<g:textArea name="description" rows="6" cols="60" value="${category?.description}"/>
<br>