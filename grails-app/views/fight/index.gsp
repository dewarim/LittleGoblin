<!DOCTYPE HTML>
<html>
<head>

	<g:render template="/shared/header" />

	
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

		<h1><g:message code="fight.index.title"/></h1>
		<div id="message" class="message">
			<g:if test="${flash.message}">${flash.message}</g:if>
		</div>
		<g:each in="${combat.mobs}" var="mob">
			<div class="fight_opponent">
				<h2><g:message code="fight.index.opponent"/></h2>
				<h3><g:message code="${mob.name}"/></h3>

				<g:render template="mob_image" model="[image:mob?.image?.image]"/>
				<br/>
				<g:message code="mob.xp"/>: ${mob.xpValue}
			</div>
		</g:each>



<h2><g:message code="fight.index.options" /></h2>
<g:link action="flee" params="[combat:combat.id]">
	<g:message code="fight.link.flee" />
</g:link>
&nbsp;|&nbsp;
<g:link action="fight" params="[combat:combat.id]">
	<g:message code="fight.link.fight" />
</g:link>


</div>
	<div class="col3">
				   <g:render template="/shared/player_character" model="[showEquipment:true]"/>

	</div>
</div>	


<g:render template="/shared/footer"/>
</body>
</html>