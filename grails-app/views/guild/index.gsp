<!DOCTYPE HTML>
<%@ page import="de.dewarim.goblin.GlobalConfigService" %>
<html>
<head>

    <meta name="layout" content="main_with_inventory"/>

</head>

<body class=" main ">

<content tag="nav">
    <ul>
        <li>
            <g:link controller="town" action="show" params="[pc: pc.id]"><g:message code="link.to.town"/></g:link>
        </li>
        <li>
            <g:link controller="guild" action="showMyGuilds"><g:message code="link.to.my_guilds"/></g:link>
        </li>
    </ul>
</content>

<div class="pc_main">

    <h1 class="guild_hall"><g:message code="guild.hall"/></h1>

    <div id="guild.description" class="guild_description">
        <g:message code="guild.hall.description"/>
    </div>

    <div id="message" class="message">
        <g:if test="${flash.message}">${flash.message}</g:if>
    </div>

    <div id="guild_list_all">
        <g:render template="/guild/guild_list"
                  model="[guilds: guilds, guildMemberService: guildMemberService, pc: pc]"/>
    </div>

</div>

</body>
</html>