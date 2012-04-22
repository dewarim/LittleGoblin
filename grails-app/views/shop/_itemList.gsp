<table class="item_list">
    <thead>
    <tr>
        <td><g:message code="shop.item.type"/></td>
        <td><g:message code="shop.item.price"/></td>
        <td><g:message code="shop.item.packageSize"/></td>
        <td><g:message code="shop.action"/></td>
    </tr>
    </thead>
    <tbody>
    <g:each in="${shopItems}" var="itemType">
        <tr>
            <td><g:message code="${itemType.name}"/></td>
            <g:set var="price" value="${shop.owner.calculatePrice(itemType)}"/>
            <td class="center">${price}</td>
            <td class="center">${itemType.packageSize}</td>
            <td>
                <g:if test="${pc.gold >= price}">
                    <g:form onsubmit="return false;">
                        <input type="hidden" name="pc" value="${pc.id}">
                        <input type="hidden" name="itemType" value="${itemType.id}">
                        <input type="hidden" name="shop" value="${shop.id}">
                            <label for="amount_${itemType.id}" style="display:none;"><g:message
                                    code="item.amount"/></label>
                            <g:textField name="amount" id="amount_${itemType.id}" size="3" value="1"/>
                        <g:submitToRemote name="buy_${itemType.id}" url="[controller:'shop', action:'buy']"
                                          update="[success:'inventory',failure:'message']"
                                          value="${message(code:'shop.item.buy')}"/>
                    </g:form>

                </g:if>
                <g:else>
                    <span class="gold_missing">
                        <g:message code="shop.gold.missing" args="[price - pc.gold]"/>
                    </span>
                </g:else>
            </td>
        </tr>

    </g:each>
    </tbody>
</table>