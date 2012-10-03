<!DOCTYPE html>
<html>
<head>
    <title><g:layoutTitle default="Grails"/></title>
    %{--<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}"/>--}%
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon"/>
    <r:require module="jquery"/>

    <link rel="stylesheet" type="text/css" href="${resource(dir:'css', file:'layout.css')}">
    <link rel="stylesheet" type="text/css" href="${resource(dir:'css', file:'goblin.css')}">

    <script type="text/javascript" src="${resource(dir: 'js', file: 'goblin.js')}"></script>
    <script type="text/javascript">

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

    </script>
    
    <g:layoutHead/>
    <r:layoutResources/>
</head>

<body>
<g:layoutBody/>
<r:layoutResources/>
</body>
</html>