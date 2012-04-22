<%@ page import="de.dewarim.goblin.quest.QuestGiver" %>
<g:set var="qid" value="${questTemplate?.id}"/>
<input type="hidden" name="id" value="${questTemplate?.id}"/>
<label><g:message code="name"/></label><br>
<g:textField name="name" value="${questTemplate?.name}"/>
<br>
<label><g:message code="description"/></label><br>
<g:textArea name="description" rows="6" cols="60" value="${questTemplate?.description}"/>
<br>
<label><g:message code="quest.template.level"/></label><br>
<g:textField name="level" value="${questTemplate?.level}"/>
<br>
<label><g:message code="quest.template.status"/></label><br>
<g:checkBox name="active" value="${questTemplate?.active}"/> <g:message code="active"/>
<br>
<label for="giver_${qid}"><g:message code="quest.template.giver"/></label>
<g:select from="${QuestGiver.listOrderByName()}" id="giver_${qid}"
		optionValue="${{message(code:it.name)}}" optionKey="id"
		name="giver" value="${questTemplate?.giver?.id}"/>
