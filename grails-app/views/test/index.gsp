<!DOCTYPE HTML>
<html>
<head>

    <meta name="layout" content="main"/>
    <asset:stylesheet src="qunit1.15.0.css"/>
    <asset:javascript src="qunit1.15.0.js"/>
    <asset:javascript src="goblin-test.js"/>
    <asset:javascript src="GoblinConfig.js"/>
    
</head>

<body class="main">

<content tag="nav">
    <g:link controller="portal" action="landing"><g:message code="link.to.home"/></g:link>
</content>

<div class="colmask ">
    <div class="col1">

    </div>

    <div class="col2">
        <h1><g:message code="test.center.head"/></h1>
        <p>
            <g:if test="${testModeEnabled}">
                <g:message code="test.mode.enabled"/>
                <script type="text/javascript">
                    var goblin = new Goblin(new GoblinConfig());
                    goblin.connect();
                    alert("The Goblin is connected: "+goblin.isConnected());
                    console.log(goblin.goToStart());
                    
                </script>
            </g:if>
            <g:else>
                <g:message code="test.mode.disabled"/>
            </g:else>
        </p>
    </div>

    <div class="col3">

    </div>
</div>

</body>
</html>