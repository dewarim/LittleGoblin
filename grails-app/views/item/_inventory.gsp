<%@ page import="de.dewarim.goblin.pc.PlayerCharacter; de.dewarim.goblin.ItemLocation" %>
<!-- inventory: -->
<script type="text/javascript">
    if (document.getElementById('pc_gold')) {
        setTextOfElement('pc_gold', '${String.valueOf(pc.gold)}');
    }
</script>

<g:if test="${pc.items.size() > 0}">
    <g:message code="item.click.to.transfer"/>
    <div id="inventory_message">
        &nbsp;
    </div>
    <table class="item_table">
        <thead>
        <tr>
            <th><g:message code="item.at.home"/></th>
            <th><g:message code="item.on.person"/></th>
            <th><g:message code="item.equipped"/></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>
                <g:set var="atHomeItems" value="${pc.items.findAll{it.location == ItemLocation.AT_HOME}}"/>
                <ul class="item_list">
                    <g:if test="${atHomeItems.size() == 0}">
                        <li><g:message code="item.empty.home"/></li>
                    </g:if>
                    <g:each in="${atHomeItems}" var="item">
                        <li id="item_${item.id}">
                            <g:form onsubmit="return false;">
                                <input type="hidden" name="item" value="${item.id}">
                                <g:if test="${item.type.stackable}">
                                    <!-- <label for="amount_${item.id}"><g:message code="item.amount"/></label> -->
                                    <g:textField name="amount" id="amount_${item.id}" size="3" value="${item.amount}"/>
                                </g:if>
                                <g:else>
                                    <input type="hidden" name="amount" value="1">
                                </g:else>
                                <g:submitToRemote name="carry_${item.id}" url="[controller:'item', action:'carryItem']"
                                                  update="[success:'inventory',failure:'inventory_message']"
                                                  value="${message(code:item.type.name)}"/>
                            </g:form>
                        </li>
                    </g:each>
                </ul>
            </td>
            <td>
                <ul class="item_list">
                    <g:each in="${pc.items.findAll{it.location == ItemLocation.ON_PERSON && ! it.equipped}}"
                            var="item">
                        <li id="item_${item.id}">
                            <g:form onsubmit="return false;">
                                <input type="hidden" name="item" value="${item.id}">
                            <g:if test="${item.type.stackable}">
                                <!-- <label for="amount_${item.id}"><g:message code="item.amount"/></label> -->
                                <g:textField name="amount" id="amount_${item.id}" size="3" value="${item.amount}"/>
                            </g:if>
                            <g:else>
                                <input type="hidden" name="amount" value="1">
                            </g:else>
                            <g:submitToRemote name="carry_${item.id}" url="[controller:'item', action:'dropItem']"
                                              update="[success:'inventory',failure:'inventory_message']"
                                              value="${message(code:item.type.name)}"/>
                            </g:form>

                            <g:if test="${(item.type.requiredSlots?.size() > 0)}">
                                <g:remoteLink action="equipItem" controller="item" class="equipLink"
                                              update="[success:'inventory', failure:'message']"
                                              params="[item:item.id, pc:pc.id]">
                                    <g:message code="equip.item"/>
                                </g:remoteLink>
                            </g:if>
                            <g:elseif test="${item.equipped}">
                                [<g:message code="item.equipped"/>]
                            </g:elseif>
                        </li>
                    </g:each>
                </ul>
            </td>
            <td>
                <ul class="item_list">
                    <g:each in="${pc.fetchEquipment()}" var="slot">
                        <li class="equipped_item">
                            <span class="slotName">
                                <g:message code="${slot.name}"/>
                            </span>
                            <span class="slotItem">
                                <g:if test="${slot.item}">

                                    <span id="eq_${slot.item.id}">
                                        <g:message code="${slot.item.type.name}"/>
                                        <g:if test="${shop}">
                                            <g:remoteLink action="unequipItem" controller="item"
                                                          update="[success:'inventory', failure:'message']"
                                                          params="[item:slot.item.id, pc:pc.id, shopId:shop?.id]">
                                                [<g:message code="equip.remove"/>]
                                            </g:remoteLink>
                                        </g:if>
                                        <g:else>
                                            <g:remoteLink action="unequipItem" controller="item"
                                                          update="[success:'inventory', failure:'message']"
                                                          params="[item:slot.item.id, pc:pc.id]">
                                                [<g:message code="equip.remove"/>]
                                            </g:remoteLink>
                                        </g:else>
                                    </span>
                                </g:if>
                                <g:else>
                                    ---
                                </g:else>
                            </span>
                        </li>
                    </g:each>
                </ul>
            </td>
        </tr>
        </tbody>
    </table>

</g:if>
<g:else>
    <g:message code="item.no.possession"/>
</g:else>