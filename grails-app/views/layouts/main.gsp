<!DOCTYPE html>
<html>
<head>
    <title><g:layoutTitle default="${grailsApplication.config.gameName ?: 'Little Goblin'}"/></title>
    <asset:link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon"/>
    <asset:stylesheet src="layout.css"/>
    <asset:stylesheet src="goblin.css"/>
    <asset:javascript src="application.js"/>
    %{--<asset:javascript src="jquery-2.1.1.js.disabled"/>--}%
    <asset:javascript src="Goblin.js"/>
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
            $('#player_messages').load('${createLink(controller:"playerCharacter", action:"fetchMessages", params:[pc:pc.id]) }');
        }
        </g:if>

    </asset:script>
    <asset:deferredScripts/>
    <g:layoutHead/>
</head>

<body class="${pageProperty(name:'body.class')}">
<g:render template="/shared/logo"/>

<g:layoutBody/>
<g:render template="/shared/footer"/>
<div id="page-info" style="display: none;" data-action="${actionName}" data-controller="${controllerName}"></div>
</body>
</html>