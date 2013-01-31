<!DOCTYPE HTML>
<html>
<head>

    <meta name="layout" content="main"/>

</head>

<body class=" main ">
<g:render template="/shared/logo"/>
<div class="navigation">
    <g:link controller="town" action="show"><g:message code="link.to.town"/></g:link>
    <g:link controller="academy" action="index"><g:message code="link.to.academies"/></g:link>
</div>

<div class="colmask ">
    <div class="col1">

        <div id="inventory" class="inventory">
            <g:render template="/shared/sideInventory"/>
        </div>

    </div>

    <div class="col2">
        <div class="pc_main">

            <h1><g:message code="${academy.name}"/></h1>

            <div id="message" class="message">
                <g:if test="${flash.message}">${flash.message}</g:if>
            </div>

            <div id="academy_description" class="description academy_description">
                <g:message code="${academy.description}"/>
            </div>

            <g:if test="${academySkillSets}">
                <h2><g:message code="academy.skillSets.h"/></h2>
                <table class="academy_skill_sets">
                    <thead>
                    <tr>
                        <th>
                            <g:message code="skillSet.name"/>
                        </th>
                        <th>
                            <g:message code="skillSet.requiredLevel"/>
                        </th>
                        <th>
                            <g:message code="skillSet.learning.time"/>
                        </th>
                        <th>
                            <g:message code="skillSet.xpPrice"/>
                        </th>
                        <th>
                            <g:message code="skillSet.goldPrice"/>
                        </th>
                        <th>
                            <g:message code="skillSet.learn.h"/>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${academySkillSets}" var="ass">
                        <tr>
                            <td>
                                <g:message code="${ass.skillSet.name}"/>
                            </td>
                            <td class="center">
                                ${ass.requiredLevel}
                            </td>
                            <td class="center">
                                ${(int) ass.skillSet.learningTime / 60000} <g:message code="unit.min"/>
                            </td>
                            <td class="center">
                                ${ass.skillSet.xpPrice}
                            </td>
                            <td class="center">
                                ${ass.skillSet.goldPrice}
                            </td>
                            <td>
                                <g:if test="${queue.containsKey(ass)}">
                                    <g:message code="skillSet.finished"
                                               args="${[g.formatDate(date: queue.get(ass).finished, type: 'datetime', style: 'MEDIUM')]}"/><br>
                                    <g:link action="stopLearning" controller="academy"
                                            params="[queueElement: queue.get(ass).id]">
                                        <g:message code="skillSet.stop.learning"/>
                                    </g:link>
                                </g:if>
                                <g:else>
                                    <g:link action="learnSkillSet" controller="academy"
                                            params="[ass: ass.id, academy: academy.id]">
                                        <g:message code="skillSet.learn"/>
                                    </g:link>
                                </g:else>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </g:if>
            <g:else>
                <div class="static_message">
                    <g:message code="academy.no.open.skillSets"/>
                </div>
            </g:else>
            <g:render template="/shared/help" model="[messageId: 'help.skillSets']"/>
        </div>
    </div>

    <div class="col3">
        <g:render template="/shared/player_character" model="[showEquipment: true]"/>
    </div>
</div>
<g:render template="/shared/footer"/>
</body>
</html>