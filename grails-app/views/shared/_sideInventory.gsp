<!-- inventory: -->

<g:if test="${pc.items.size() > 0}">

    <h2><g:message code="pc.inventory"/></h2>
    <table>
        <g:each in="${pc.items}" var="item">
            <g:set var="hideRow" value="hideRow('item_${item.id}')"/>
            <g:if test="${! item.equipped}">
                <tr id="item_${item.id}">
                    <td><g:if test="${item.type.stackable}">
                        ${item.amount}
                    </g:if>
                    </td>
                    <td><g:message code="${item.type.name}"/></td>
                    <td class="equip_cell">
                        <g:if test="${(item.type.requiredSlots?.size() > 0) && ! item.equipped}">
                            <g:remoteLink action="equipItem" controller="item"
                                          update="[success:'equipment', failure:'message']" onLoaded="${hideRow}"
                                          params="[item:item.id, pc:pc.id, shop:shop?.id ?:0]">
                                <g:message code="equip.item"/>
                            </g:remoteLink>
                        </g:if>
                        <g:else>&nbsp;</g:else>
                    </td>
                    <td class="sell_cell">
                        <g:if test="${shop}">
                            <g:form onsubmit="return false;">
                                <input type="hidden" name="pc" value="${pc.id}">
                                <input type="hidden" name="item" value="${item.id}">
                                <input type="hidden" name="shop" value="${shop.id}">
                                <g:if test="${item.type.stackable}">
                                    <label for="amount_${item.id}" style="display:none;"><g:message
                                            code="item.amount"/></label>
                                    <g:textField name="amount" id="amount_${item.id}" size="3" value="${item.amount}"/>
                                </g:if>
                                <g:else>
                                    <input type="hidden" name="amount" value="1">
                                </g:else>
                                <g:submitToRemote name="sell_${item.id}" url="[controller:'shop', action:'sell']"
                                                  update="[success:'inventory',failure:'inventory_message']"
                                                  value="${message(code:'item.sell', args:[shop.owner.calculateSellPrice(item)])}"/>
                            </g:form>

                        </g:if>
                    </td>
                    <td>
                        <!-- use_cell -->
                        <g:if test="${item.type.usable && combat}">
                            <g:each in="${item.type.itemTypeFeatures}" var="itemFeature">
                                <g:link action="useItem" controller="item"
                                        params="[combat:combat?.id, item:item.id, feature:itemFeature.feature.id]">
                                    [ <g:message code="${itemFeature.feature.name}"/> (${item.uses}) ]
                                </g:link>
                            </g:each>
                        </g:if>
                    </td>
                </tr>
            </g:if>
        </g:each>
    </table>

</g:if>
<script type="text/javascript">
    if (document.getElementById('pc_gold')) {
        setTextOfElement('pc_gold', '${String.valueOf(pc.gold)}');
    }
</script>