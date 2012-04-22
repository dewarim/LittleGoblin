<%@ page import="de.dewarim.goblin.pc.skill.Skill" %>
<input type="hidden" name="product" value="${product.id}">
<input type="hidden" name="id" value="${requirement?.id}">
<label for="level_${requirement?.id}"><g:message code="requirement.level"/></label>
<g:textField name="level" value="${requirement?.level}" id="level_${requirement?.id}"/>
<label for="skill_${requirement?.id}"><g:message code="requirement.skill"/></label>
<g:select from="${Skill.listOrderByName()}" name="skill" optionValue="${{message(code:it.name)}}" optionKey="id" value="${requirement?.skill?.id}"/>

