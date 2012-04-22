<%@ page import="de.dewarim.goblin.town.Academy" %>
		<input type="hidden" name="id" value="${guild?.id}"/>
<label><g:message code="guild.name"/> </label><br>
<g:textField name="name" value="${guild?.name}"/>
<br>
<label><g:message code="guild.description"/> </label><br>
<g:textArea name="description" rows="6" cols="60" value="${guild?.description}"/>
<br>
<label><g:message code="guild.entryFee"/> </label><br>
<g:textField name="entryFee" value="${guild?.entryFee ?: 1}"/>  <g:message code="guild.entryFee.default"/>
<br>
<label><g:message code="guild.incomeTax"/> </label><br>
<g:textField name="incomeTax" value="${guild?.incomeTax ?: 0.99}"/> <g:message code="guild.incomeTax.default"/>
<br>
<br>
<label><g:message code="guild.academies"/> </label><br>
<g:select name="academies" from="${Academy.list()}"  value="${guild?.guildAcademies*.guild}"  optionKey="id" optionValue="name" multiple="true"/>
<br>
