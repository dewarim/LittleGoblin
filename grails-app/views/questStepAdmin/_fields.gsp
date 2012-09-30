<%@ page import="de.dewarim.goblin.quest.QuestStep; de.dewarim.goblin.quest.Encounter; de.dewarim.goblin.quest.QuestTemplate" %>
<g:set var="qid" value="${questStep?.id}"/>
<input type="hidden" name="id" value="${questStep?.id}"/>
<label><g:message code="name"/> </label><br>
<g:textField name="name" value="${questStep?.name}"/>
<br>
<label><g:message code="questStep.title"/> </label><br>
<g:textField name="title" value="${questStep?.title}"/>
<br>
<label><g:message code="description"/> </label><br>
<g:textArea name="description" rows="6" cols="60" value="${questStep?.description}"/>
<br>
<label><g:message code="questStep.intro"/> </label><br>
<g:textArea name="intro" rows="6" cols="60" value="${questStep?.intro}"/>
<br>
<label for="questStep.eoc.${qid}"><g:message code="questStep.endOfQuest"/></label>
<g:checkBox name="endOfQuest" id="questStep.eoc.${qid}" value="${questStep?.endOfQuest}"/>
<br>
<label><g:message code="questStep.questTemplate"/> </label>
<g:select from="${QuestTemplate.listOrderByName()}" name="questTemplate"
	    optionValue="${{message(code:it.name)}}" optionKey="id"
		value="${questStep?.questTemplate?.id}"/>
<br>
<label><g:message code="questStep.encounter"/> </label>
<g:select from="${Encounter.listOrderByName()}" name="encounter"
	    optionValue="${{message(code:it.name)}}" optionKey="id"
		value="${questStep?.encounter?.id}"/>
<br>
<label for="parentSteps_${qid}"><g:message code="questStep.parentSteps"/> </label><br>
<g:select from="${newSteps}" id="parentSteps_${qid}"
		optionValue="${{message(code:it.name)}}" optionKey="id"
		name="parentStep" multiple="true" value="${parentSteps*.id}"/>
<br>
<label for="nextSteps_${qid}"><g:message code="questStep.nextSteps"/> </label><br>
<g:select from="${newSteps}" id="nextSteps_${qid}"
		optionValue="${{message(code:it.name)}}" optionKey="id"
		name="nextStep" multiple="true" value="${childSteps*.id}"/>
