<!DOCTYPE HTML>
<html>
<head>

    <meta name="layout" content="test"/>

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
            <g:if test="${testModeEnabled}">
                <g:message code="test.mode.enabled"/>
                <script type="text/javascript">
                    var goblin = new Goblin(new GoblinConfig());
                    runTests(goblin);
                </script>
                
                <h2><g:message code="test.results"/></h2>
                <div id="qunit">
                    
                </div>
                
            </g:if>
            <g:else>
                <p>
                    <g:message code="test.mode.disabled"/>    
                </p>
            </g:else>
    </div>

    <div class="col3">

    </div>
</div>

</body>
</html>