<!DOCTYPE HTML>
<html>
<head>
    <meta name="layout" content="main_with_inventory"/>

</head>

<body class="main">

<content tag="nav"></content>

<h1><g:message code="fight.index.title"/></h1>

<div id="message" class="message">
    <g:if test="${flash.message}">${flash.message}</g:if>
</div>
<g:each in="${combat.mobs}" var="mob">
    <div class="fight_opponent">
        <h2><g:message code="fight.index.opponent"/></h2>

        <h3><g:message code="${mob.name}"/></h3>

        <g:render template="mob_image" model="[image: mob?.image?.image]"/>
        <br/>
        <g:message code="mob.xp"/>: ${mob.xpValue}
    </div>
</g:each>

<h2><g:message code="fight.index.options"/></h2>
<g:link action="flee" params="[combat: combat.id]">
    <g:message code="fight.link.flee"/>
</g:link>
&nbsp;|&nbsp;
<g:link action="fight" params="[combat: combat.id]">
    <g:message code="fight.link.fight"/>
</g:link>

</body>
</html>