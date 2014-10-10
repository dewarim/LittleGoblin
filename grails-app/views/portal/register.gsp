<!DOCTYPE HTML>
<html>
<head>

    <meta name="layout" content="main"/>
   	
</head>

<body class="main">


<div class="navigation">
    <g:link controller="portal" action="landing"><g:message code="link.to.home"/></g:link><br>
</div>

<div class="registration">

    <h1><g:message code="registration.head"/></h1>
    <g:if test="${flash.message}">
        <div id="message" class="message">
            ${flash.message}
        </div>
    </g:if>
    <g:else>
        <div class="info">
            <g:if test="${grailsApplication.config.testMode}">
                <div style="background-color: #f5deb3; padding: 1ex; border: 1px black dotted; margin-bottom: 1ex;">
                    <g:message code="registration.testMode"/>
                </div>
            </g:if>
            <g:message code="registration.info" args="[nameOfTheGame]"/>
        </div>
    </g:else>
    
    <div id="registrationForm">
        <g:render template="registrationForm" model="[username:username, email:email, problems:problems]"/>    
    </div>

</div>


</body>
</html>