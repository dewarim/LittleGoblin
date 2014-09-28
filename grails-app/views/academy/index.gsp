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

    <h1><g:message code="academy.list.h"/></h1>

    <div id="message" class="message">
        <g:if test="${flash.message}">${flash.message}</g:if>
    </div>

    <div id="academy_intro" class="description">
        <g:message code="academy.list.description"/>
    </div>
    <br>

    <div id="academy_list_all">
        <g:render template="/academy/academy_list"
                  model="[academies: academies, pc: pc, academyCount: academyCount, max: max, offset: offset]"/>
    </div>

</div>

</body>
</html>