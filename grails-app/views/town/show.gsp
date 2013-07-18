<%@ page import="de.dewarim.goblin.QuestService" %>
<!DOCTYPE HTML>
<html>
<head>

    <meta name="layout" content="main"/>
   	

</head>

<body class=" main town ">

<div class="navigation">
    <ul class="nav_list">
        <li>
            <g:link controller="portal" action="start"><g:message code="link.to.portal_start"/></g:link>
        </li>
        <li>
            <g:link controller="playerCharacter" action="show" params="[pc:pc.id]"><g:message
                    code="link.to.pc"/></g:link>
        </li>
        <li>
            <g:link controller="production" action="workshop"><g:message code="link.to.workshop"/></g:link>
        </li>
        <li>
            <g:link controller="item" action="showInventory"><g:message code="link.to.inventory"/></g:link>
        </li>
    </ul>
</div>

<div class="colmask ">
    <div class="col1">

        <div id="inventory" class="inventory">
            <g:render template="/shared/sideInventory"/>
        </div>

    </div>

    <div class="col2">
        <div class="town">

            <h1><g:message code="${pc.town.name}"/></h1>

            <div class="town_description">
                <g:message code="${pc.town.description}"/>
            </div>

            <div id="message" class="message">
                <g:if test="${flash.message}">${flash.message}</g:if>
            </div>

            <h2><g:message code="town.quest.givers"/></h2>
            <ul class="quest_master">
                <g:each in="${questMasters}" var="master">
                    <g:set var="questCount"
                           value="${questService.fetchAvailableQuestsByQuestGiver(master, pc)?.size()}"/>
                    <li>
                        <g:link controller="quest" action="showQuestMaster"
                                params="[pc:pc.id, questMaster:master.id]">
                            <g:message code="${master.name}"/> (${questCount})
                        </g:link>
                    </li>
                </g:each>
            </ul>

            <h2><g:message code="town.shops"/></h2>
            <g:if test="${town.shops?.size() > 0}">
                <ul>
                    <g:each in="${town.shops}" var="shop">
                        <li><g:link action="show" controller="shop" params="[shop:shop.id, pc:pc.id]">
                            <g:message code="${shop.name}"/>
                        </g:link></li>
                    </g:each>

                </ul>
            </g:if>
            <g:else>
                <g:message code="town.no_shops"/>
            </g:else>
            <h2><g:message code="town.other.functions"/></h2>

            <div id="order_link">
                <ul class="no_bullets">
                    <li>
                        <g:link action="index" controller="goblinOrderAdmin">
                            <g:message code="town.order.link"/>
                        </g:link>
                    </li>
                    <li>
                        <g:link action="index" controller="mailBox">
                            <g:message code="mail.link.to"/>(${mailCount})
                        </g:link>
                    </li>
                    <li>
                        <g:link action="index" controller="postOffice">
                            <g:message code="post.link.to"/>
                        </g:link>
                    </li>
                    <li>
                        <g:link action="index" controller="guild">
                            <g:message code="town.guild.link"/>
                        </g:link>
                    </li>
                    <li>
                        <g:link action="index" controller="academy">
                            <g:message code="town.academy.link"/>
                        </g:link>
                    </li>
                    <li>
                        <g:link controller="melee" action="index">
                            <g:message code="town.melee.link"/>
                        </g:link>
                    </li>
                </ul>
            </div>
        </div>
    </div>

    <div class="col3">
        <g:render template="/shared/player_character" model="[showEquipment:true]"/>
    </div>
</div>

</body>
</html>