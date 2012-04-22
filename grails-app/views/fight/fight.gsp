<!DOCTYPE HTML>
<html>
<head>
	<g:render template="/shared/header"/>

	<!--
 <meta http-equiv="refresh" content="5;URL=/goblin/fight/fight?pc=${pc.id}&mob=${mob.id}&combat=${combat.id}"/>
-->

</head>
<body class="main">
<g:render template="/shared/logo"/>
     <div class="navigation">
	 </div>
<div class="colmask ">
	<div class="col1">

		<div id="inventory" class="inventory">
		  <g:render template="/shared/sideInventory"/>
		</div>

	</div>
	<div class="col2">

		<h1><g:message code="fight.fight.title" args="[pc.name, message(code:mob.name)]"/></h1>
		<div id="message" class="message">
				<g:if test="${flash.message}">${flash.message}</g:if>
		</div>

		<div class="fight_opponent">
			<g:render template="fight_mob" model="[mob:mob]"/>
		</div>
		<div class="nav">
			<g:link action="flee" params="[combat:combat.id]"><g:message code="fight.link.flee"/></g:link>
			&nbsp;|&nbsp;
			<g:link action="fight" params="[combat:combat.id]"><g:message code="fight.link.fight"/></g:link>
		</div>

	<g:render template="/shared/combat_messages"/>

	</div>
	<div class="col3">
		<g:render template="/shared/player_character" model="[showEquipment:true]"/>
	</div>
</div>
<g:render template="/shared/footer" />
</body>
</html>