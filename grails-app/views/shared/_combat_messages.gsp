<div class="messages combat_messages">
	<h2><g:message code="fight.fight.messages"/></h2>

	<div class="message_list">
		<g:each in="${combat.combatMessages.sort{a,b -> b.id <=> a.id}}" var="m">
			<g:message code="${m.msg}" args="${m.fetchArgs().collect{message(code:it)}}"/>
			<br/>
		</g:each>
	</div>
</div>