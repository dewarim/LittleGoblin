<%@ page import="de.dewarim.goblin.mob.MobTemplate; de.dewarim.goblin.GoblinScript" %>
<input type="hidden" name="id" value="${encounter?.id}"/>
<label><g:message code="name"/> </label><br>
<g:textField name="name" value="${encounter?.name}"/>
<br>
<label><g:message code="encounter.includesCombat"/></label><br>
<g:checkBox name="includesCombat" value="${encounter?.includesCombat}"/> <g:message code="encounter.confirm.combat"/>
<br>
<label><g:message code="encounter.script"/> </label><br>
<g:select from="${GoblinScript.listOrderByName()}" name="script"
		optionValue="${{message(code:it.name)}}" optionKey="id"
	 	noSelection="${['null':'---']}"
		value="${encounter?.script?.id}"/>
<br>
<label><g:message code="encounter.config"/> </label><br>
<g:textArea name="config" rows="6" cols="60" value="${encounter?.config}"/>
<br>
<label><g:message code="encounter.mobs"/> </label><br>
<g:select from="${MobTemplate.listOrderByName()}"
		optionValue="${{message(code:it.name)}}" optionKey="id"
		name="mob" multiple="true" value="${mobList*.id}"/>
<br>



