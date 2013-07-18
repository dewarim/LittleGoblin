<%@ page import="de.dewarim.goblin.pc.PlayerCharacter;" %>
		<input type="hidden" name="id" value="${goblinOrder?.id}"/>
<label><g:message code="name"/> </label><br>
<g:textField id="" name="name" value="${goblinOrder?.name}"/>
<br>
<label><g:message code="description"/> </label><br>
<g:textArea name="description" rows="6" cols="60" value="${goblinOrder?.description}"/>
<br>
<label><g:message code="goblinOrder.score"/> </label><br>
<g:textField name="score" value="${goblinOrder?.score ?: 0}"/>  <g:message code="goblinOrder.score"/>
<br>
<label><g:message code="goblinOrder.coins"/> </label><br>
<g:textField name="coins" value="${goblinOrder?.coins ?: 0}"/> <g:message code="goblinOrder.coins"/>
<br>
<br>
<label><g:message code="goblinOrder.leader.search"/> </label>:<br>
<g:remoteField name="leader-select" update="order-leader" value=""  
    controller="playerCharacter" action="listRemote" paramName="name" id="leader.id"/>
    <div id="order-leader">
        <g:render template="/playerCharacter/listRemote" model="[users:[], fieldName:'leader.id']"/>
    </div>
<br>
