<!DOCTYPE html>
<html>
<head>
    <title><g:layoutTitle default="${grailsApplication.config.gameName ?: 'Little Goblin'}"/></title>
    <asset:link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon"/>
    <asset:stylesheet src="layout.css"/>
    <asset:stylesheet src="goblin.css"/>
    <asset:javascript src="application.js"/>
    %{--<asset:javascript src="jquery-2.1.1.js.disabled"/>--}%
    <asset:javascript src="goblin.js"/>
    <asset:script type="text/javascript">

        function hideDiv(id) {
            $("#"+id).css('display','none');
        }

        function showDiv(id) {
            $("#"+id).css('display','block');
        }

        <g:if test="${pc}">
            /* This looks for updates to player_messages every 5 seconds. */
            function messageReload(){
                $('#player_messages').load('${createLink(controller: "playerCharacter", action: "fetchMessages", params: [pc: pc.id])}');
        }
        </g:if>

    </asset:script>
    <asset:deferredScripts/>
    <g:layoutHead/>
</head>

<body>
<g:render template="/shared/logo"/>

<div class="navigation">
    <g:pageProperty name="page.nav"/>
</div>

<div class="colmask ">
    <div class="col1">

        <div id="inventory" class="inventory">
            <g:render template="/shared/sideInventory" model="[pc: pc, items:items ?: pc.items]"/>
        </div>

    </div>

    <div class="col2">

        <g:layoutBody/>

    </div>

    <div class="col3">
        <g:render template="/shared/player_character" model="[showEquipment: true]"/>
    </div>
</div>
<g:render template="/shared/footer"/>

</body>
</html>