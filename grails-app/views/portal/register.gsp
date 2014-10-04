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

    <g:form controller="portal" action="doRegister">
        <table class="registration_form">
            <tbody>
            <tr>
                <td><label for="r_username">
                    <g:message code="registration.username"/>
                </label>
                </td>
            </tr><tr>
                <td><input type="text" id="r_username" name="username"
                           value="${username}" title="${message(code:'registration.minNameLength', args:[minNameLength])}"></td>
            </tr>
            <tr>
                <td><label for="r_email">
                    <g:message code="registration.email"/>
                </label></td>
            </tr><tr>
                <td><input type="text" id="r_email" name="email" value="${email}"
                     title="${message(code:'registration.need.email')}"></td>
            </tr>
            <tr>
                <td><label for="r_password">
                    <g:message code="registration.password"/>
                </label>
                </td>
            </tr><tr>
                <td><input type="password" id="r_password" name="password"
                           title="${message(code:'registration.minPassLength', args:[minPassLength])}"></td>
            </tr>
            <tr>
                <td><label for="r_password2"><g:message code="registration.password2"/></label>
                </td>
            </tr><tr>
                <td><input type="password" id="r_password2" name="password2" title="${message(code:'registration.repeat.pass')}"></td>
            </tr>
            <tr>
                <td class="right"><input type="submit" name="register" value="${message(code: 'registration.submit')}">
                </td>
            </tr>
            </tbody>
        </table>
    </g:form>

</div>


</body>
</html>