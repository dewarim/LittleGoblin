<!DOCTYPE HTML>
<html>
<head>

    <g:render template="/shared/header"/>

</head>

<body class=" main shop ">
<g:render template="/shared/logo"/>
<div class="navigation">
    <g:link controller="town" action="show" params="[pc:pc.id]"><g:message code="link.to.town"/></g:link>
</div>

<div class="colmask ">
    <div class="col1">
        <div id="inventory">
            <g:render template="/shared/sideInventory" model="[pc:pc]"/>
        </div>
    </div>

    <div class="col2">
        <div class="shop">
            <h1><g:message code="${shop.name}"/></h1>


            <div id="message" class="message">
                <g:if test="${flash.message}">${flash.message}</g:if>
            </div>


            <div class="shop description">
                <g:message code="${shop.description}"/>
            </div>

            <h2><g:message code="${shop.owner.name}"/></h2>

            <div class="shop_owner description">
                <g:message code="${shop.owner.description}"/>
            </div>

            <!-- Items in the shop -->
            <h2><g:message code="shop.items"/></h2>

            <g:render template="categoryFilter" model="[pc:pc, categories:categories, shop:shop]"/>

            <br>

            <g:if test="${shop.itemTypes?.size() > 0}">
                <div id="itemList">
                    <g:render template="itemList" model="[pc:pc, shopItems:shopItems, shop:shop]"/>
                </div>
            </g:if>
            <g:else>
                <g:message code="shop.no_items"/>
            </g:else>

        <!-- end of items list -->

        </div>

    </div>

    <div class="col3">
        <g:render template="/shared/player_character" model="[showEquipment:true]"/>
    </div>
</div>
<g:render template="/shared/footer"/>
</body>
</html>