<!DOCTYPE HTML>
<html>
<head>

    <meta name="layout" content="main_with_inventory"/>

</head>

<body class=" main ">

<content tag="nav">
    <g:link controller="town" action="show" params="[pc: pc.id]"><g:message code="link.to.town"/></g:link>
</content>

<div class="mail">

    <h1 class="mail"><g:message code="mail.h"/></h1>

    <div id="message" class="message">
        <g:if test="${flash.message}">${flash.message}</g:if>
    </div>

    <div id="box_view" class="box_view">
        <g:render template="/mailBox/mailbox" model="['mailBox': mailBox]"/>
    </div>

    <div id="newMailLink">
        <g:link action="writeMail" controller="mailBox">
            <g:message code="mail.write.link"/>
        </g:link>
    </div>

</div>

</body>
</html>