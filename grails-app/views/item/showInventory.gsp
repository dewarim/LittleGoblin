<%@ page import="de.dewarim.goblin.ItemLocation" %>
<!DOCTYPE HTML>
<html>
<head>
    <title><g:message code="showInventory.title"/></title>
    <meta name="layout" content="main"/>
    <g:render template="/shared/header"/>
    
</head>

<body class=" main ">
<g:render template="/shared/logo"/>

    <div class="navigation">
        <ul class="nav_list">
            <li>
                <g:link controller="town" action="show" params="[pc:pc.id]"><g:message code="link.to.town"/></g:link>
            </li>
        </ul>
    </div>
 <div class="colmask ">
    <div class="col1">

    </div>

    <div class="col2">
        <div class="inventory_main">

            <h1><g:message code="item.inventory"/></h1>

            <div id="message" class="message">
                <g:if test="${flash.message}">${flash.message}</g:if>
            </div>

            <div id="inventory" class="inventory">
                <g:render template="inventory" model="[pc:pc]"/>
            </div>

        </div>

        <div class="col3">

            <g:render template="/shared/player_character" model="[showEquipment:false]"/>
        </div>
    </div>
</div>
   <g:render template="/shared/footer"/>
</body>
</html>