<%@ page import="de.dewarim.goblin.town.Town; de.dewarim.goblin.guild.Guild" %>
<input type="hidden" name="id" value="${academy?.id}"/>
<input type="hidden" name="indirectSubmit" value="update">
<label><g:message code="academy.name"/></label><br>
<br>
<g:textField name="name" value="${academy?.name}"/>
<br>
<label><g:message code="academy.description"/></label><br>
<g:textArea name="description" rows="6" cols="60" value="${academy?.description}"/>
<br>
<label><g:message code="academy.town"/></label>
<br>
<g:select name="town" from="${Town.list()}" optionKey="id" optionValue="name" value="${academy?.town?.id}"/>
<br>
<label><g:message code="academy.guilds"/></label>
<br>
<g:select name="guilds" from="${Guild.list()}" value="${academy?.guildAcademies*.guild}" optionKey="id" optionValue="name" multiple="true"/>
