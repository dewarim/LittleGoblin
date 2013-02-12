<!DOCTYPE HTML>
<%@ page import="de.dewarim.goblin.GlobalConfigService" %>
<html>
<head>

	<meta name="layout" content="main"/>
   	


</head>
<body class=" main ">

    <div class="navigation">
        <g:link controller="town" action="show"><g:message code="link.to.town"/></g:link>
        <g:link controller="guild" action="index"><g:message code="link.to.guilds"/></g:link>
        <g:link controller="guild" action="showMyGuilds"><g:message code="link.to.my_guilds"/></g:link>
    </div>
<div class="colmask ">
	<div class="col1">

		<div id="inventory" class="inventory">
			<g:render template="/shared/sideInventory"/>
		</div>

	</div>
	<div class="col2">
		<div class="pc_main">

			<h1 class="guild"><g:message code="guild.h" args="[message(code:guild.name)]"/></h1>

			<div id="message" class="message">
				<g:if test="${flash.message}">${flash.message}</g:if>
			</div>

			<div id="guild.description" class="guild_description description">
				<g:message code="${guild.description}"/>
			</div>

			<g:if test="${guild.guildAcademies.size() > 0}">

				<h2><g:message code="guild.academies.h"/></h2>
				<div id="guild_academies" class="description">
					<g:message code="guild.academy.intro"/>
				</div>
				<div class="guild_academy_list">
					<ul>

						<g:each in="${guild.guildAcademies.collect{it.academy}}" var="academy">
							<li>
								<g:link action="show" controller="academy" params="[academy:academy.id]">
									<g:message code="${academy.name}"/>
								</g:link>
							</li>
						</g:each>
					</ul>
				</div>
				
			</g:if>
			<g:else>
				<div id="guild_academies" class="description">
					<g:message code="guild.academy.none"/>
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