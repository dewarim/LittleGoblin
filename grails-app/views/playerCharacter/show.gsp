<!DOCTYPE HTML>
<html>
<head>

    <meta name="layout" content="main_with_inventory"/>

</head>

<body class=" main ">

<content tag="nav">
    <g:link controller="town" action="show" params="[pc: pc.id]"><g:message code="link.to.town"/></g:link>
</content>

<div class="pc_main">

    <h1><g:message code="${pc.name}"/></h1>

    <div id="message" class="message">
        <g:if test="${flash.message}">${flash.message}</g:if>
    </div>

    <h2><g:message code="pc.about"/></h2>

    <div id="pc_description" class="description pc_description">
        <g:render template="/playerCharacter/showDescription"/>
    </div>

    <h2><g:message code="pc.reputation"/></h2>
    <g:if test="${pc.reputations?.isEmpty()}">
        <g:message code="pc.no.reputation"/>
    </g:if>
    <g:else>
        <table class="reputation_list" border="1">
            <tr>
                <th><g:message code="reputation.faction"/></th>
                <th><g:message code="reputation.level"/></th>
                <th><g:message code="reputation.description"/></th>
            </tr>
            <g:each in="${pc.reputations.sort { message(code: it.faction.name) }}" var="rep">
                <tr>
                    <td><g:message code="${rep.faction.name}"/></td>
                    <td>${rep.level}</td>
                    <td><g:message code="${rep.faction.fetchDescription(rep.level)}"/></td>
                </tr>
            </g:each>
        </table>
    </g:else>
    <h2><g:message code="pc.skills.h"/></h2>
    <g:if test="${pc.creatureSkills?.isEmpty()}">
        <g:message code="pc.no.skills"/>
    </g:if>
    <g:else>
        <g:if test="${!combatSkills.isEmpty()}">
            <g:render template="/playerCharacter/combatSkills" model="[combatSkills: combatSkills]"/>
        </g:if>
        <g:if test="${!productionSkills.isEmpty()}">
            <g:render template="/playerCharacter/productionSkills" model="[productionSkills: productionSkills]"/>
        </g:if>
    </g:else>
</div>
</body>
</html>