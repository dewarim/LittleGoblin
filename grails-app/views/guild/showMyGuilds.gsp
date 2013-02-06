<!DOCTYPE HTML>
<%@ page import="de.dewarim.goblin.GlobalConfigService" %>
<html>
<head>

	<meta name="layout" content="main"/>
   	

</head>
<body class=" main ">
<g:render template="/shared/logo"/>
    <div class="navigation">
        <g:link controller="town" action="show" params="[pc:pc.id]"><g:message code="link.to.town"/></g:link>
        <g:link controller="guild" action="index"><g:message code="link.to.guilds"/></g:link>
    </div>
<div class="colmask ">
	<div class="col1">

		<div id="inventory" class="inventory">
			<g:render template="/shared/sideInventory"/>
		</div>

	</div>
	<div class="col2">
		<div class="pc_main">

			<h1 class="guild_hall"><g:message code="guild.my_guilds.h" args="[pc.name]"/></h1>

			<div id="message" class="message">
				<g:if test="${flash.message}">${flash.message}</g:if>
			</div>

			<g:if test="${guilds.isEmpty()}">
				<div id="my_guild_description" class="guild_description">

					<g:message code="guild.mine.none"/>
				</div>
			</g:if>
			<g:else>
				<div id="guild_list_all">
					<g:render template="/guild/guild_list_mine" model="[guilds:guilds, pc:pc]"/>
				</div>
			</g:else>

		</div>
	</div>
	<div class="col3">
		<g:render template="/shared/player_character" model="[showEquipment:true]"/>
	</div>
</div>

</body>
</html>