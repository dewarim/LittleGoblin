<!DOCTYPE HTML>
<html>
<head>

    <meta name="layout" content="main"/>
   	

</head>

<body class=" main quest ">
<g:render template="/shared/logo"/>
<div class="navigation">
</div>

<div class="colmask ">
    <div class="col1">

    </div>

    <div class="col2">
        <h1><g:message code="${questMaster.name}"/></h1>
        <g:if test="${quests?.size() > 0}">
            <ul class="quest_list">
                <g:each in="${quests}" var="qt">
                    <li>
                        <g:link action="describeQuest" controller="quest" params="[quest:qt.id, pc:pc.id]">
                            <g:message code="${qt.name}"/></g:link>
                    </li>
                </g:each>
            </ul>
        </g:if>
        <g:else>
            <g:message code="town.no_quests"/>
        </g:else>

    </div>

    <div class="col3">
        <g:render template="/shared/player_character" model="[showEquipment:true]"/>
    </div>
</div>

</body>
</html>