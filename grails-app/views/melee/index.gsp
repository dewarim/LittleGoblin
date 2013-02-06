<!DOCTYPE HTML>
<html>
<head>

    <meta name="layout" content="main"/>
   	

</head>

<body class=" main  ">
<g:render template="/shared/logo"/>
     <div class="navigation">
        <g:link controller="town" action="show" params="[pc:pc.id]"><g:message code="link.to.town"/></g:link>
    </div>
<div class="colmask ">
    <div class="col1">

    </div>

    <div class="col2">
        <div class="melee">
            <h1><g:message code="melee.title"/></h1>

            <div id="message" class="message">
                <g:if test="${flash.message}">${flash.message}</g:if>
            </div>

            <div class="melee description">
                <g:message code="melee.description"/>
                <br>
                <g:render template="/shared/help" model="[messageId:'melee.info']"/>
            </div>

            <div class="note">
                <strong>Note: The Grand Melee is not yet implemented fully. We are working on it.</strong>
            </div>

            <div class="spacer1">&nbsp;</div>

            <g:if test="${waitingMelee}">
                <h2><g:message code="melee.waiting"/></h2>
                <g:if test="${pc.currentMelee == waitingMelee}">
                    <g:message code="melee.player.waiting"/><br>
                    <g:link controller="melee" action="leave" params="[pc:pc.id]">
                        <g:message code="melee.link.leave"/>
                    </g:link>
                </g:if>
                <g:else>
                    <g:message code="melee.invitation"/><br>
                    <g:link controller="melee" action="join" params="[pc:pc.id]">
                        <g:message code="melee.link.join"/>
                    </g:link>
                </g:else>
                <div id="melee_fighters" class="fighters">
                    <g:render template="fighters" model="[fighters:fighters]"/>
                </div>
                <script type="text/javascript">
                    /*
                     * Reload the page every 20s to see if the melee has started.
                     */
                    function reloadPage(){
                        location.reload();
                    }
                    var pageReload = setTimeout("reloadPage()", 20000);
                </script>
            </g:if>
            <g:elseif test="${runningMelee}">
                <h2><g:message code="melee.running"/></h2>
                <g:if test="${pc.currentMelee == runningMelee}">
                    <div id="chooseAction">
                        <g:render template="chooseAction" model="[melee:pc.currentMelee, pc:pc, opponents:opponents, items:items, currentAction:currentAction]"/>
                    </div>
                </g:if>
                <g:else>
                    <g:message code="melee.invitation.late"/><br>
                    <g:link controller="melee" action="join" params="[pc:pc.id]">
                        <g:message code="melee.link.join"/>
                    </g:link>
                </g:else>
                <div id="meleeFighters" class="fighters">
                    <g:render template="fighters" model="[fighters:fighters]"/>
                </div>
            </g:elseif>
            <g:else>
                <g:message code="melee.peace"/>
            </g:else>

        </div>
    </div>

    <div class="col3">
        <g:render template="/shared/player_character" model="[showEquipment:true]"/>
    </div>
</div>

</body>
</html>