<%@ page import="de.dewarim.goblin.Dice; de.dewarim.goblin.Image; de.dewarim.goblin.mob.MobTemplate" %>
<g:set var="mid" value="${mob?.id}"/>
<input type="hidden" name="id" value="${mob?.id}"/>
<label><g:message code="name"/> </label><br>
<g:textField name="name" value="${mob?.name}"/>
<br>
<label for="description_${mid}"><g:message code="description"/> </label><br>
<g:textField id="description_${mid}" name="description" value="${mob?.description}"/>
<br>
<label for="xpValue_${mid}"><g:message code="mob.xpValue"/> </label><br>
<g:textField id="xpValue_${mid}" name="xpValue" value="${mob?.xpValue ?: 1}"/>
<br>
<label><g:message code="mob.strike"/> </label><br>
<g:select from="${Dice.listOrderByName()}" name="strike"
		optionKey="id" optionValue="${{it.toString() +' :: '+message(code:it.name) }}"
		noSelection="${[null:'---']}"
		value="${mob?.strike?.id}"/>
<br>
<label><g:message code="mob.parry"/> </label><br>
<g:select from="${Dice.listOrderByName()}" name="parry"
		optionKey="id" optionValue="${{it.toString() +' :: '+message(code:it.name) }}"
		noSelection="${[null:'---']}"
		value="${mob?.parry?.id}"/>
<br>
<label><g:message code="mob.damage"/> </label><br>
<g:select from="${Dice.listOrderByName()}" name="damage"
		optionKey="id" optionValue="${{it.toString() +' :: '+message(code:it.name) }}"
		noSelection="${[null:'---']}"
		value="${mob?.damage?.id}"/>
<br>
<label><g:message code="mob.initiative"/> </label><br>
<g:select from="${Dice.listOrderByName()}" name="initiative"
		optionKey="id" optionValue="${{it.toString() +' :: '+ message(code:it.name) }}"
		noSelection="${[null:'---']}"
		value="${mob?.initiative?.id}"/>
<br>
<label><g:message code="mob.hp"/> </label><br>
<g:textField name="hp" value="${mob?.hp ?: '1'}"/>
<br>
<label><g:message code="mob.maxHp"/> </label><br>
<g:textField name="maxHp" value="${mob?.maxHp ?: '2'}"/>
<br>
<label><g:message code="mob.gold"/> </label><br>
<g:textField name="gold" value="${mob?.gold ?: '0'}"/>
<br>
<label><g:message code="mob.male"/></label><br>
<g:checkBox name="male" value="${mob?.male}"/> <g:message code="mob.confirm.male"/>
<br>
<label><g:message code="mob.images"/> </label><br>
<g:select from="${Image.listOrderByName()}" name="image"
		optionValue="${{message(code:it.name)}}" optionKey="id"
	multiple="true"
		value="${imageList*.id}"/>
<br>
