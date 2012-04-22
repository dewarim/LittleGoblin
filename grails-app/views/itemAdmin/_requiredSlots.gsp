<%@ page import="de.dewarim.goblin.EquipmentSlotType; de.dewarim.goblin.RequiredSlot" %>
<h2><g:message code="item.requiredSlots"/></h2>
<g:if test="${updated}">
    <p><g:message code="slots.were.updated"/></p>
</g:if>
<g:form>
    <input type="hidden" name="id" value="${itemType.id}">
    <table>
<thead>
<tr>
    <th><g:message code="slot.type"/></th>
    <th><g:message code="slots.amount"/></th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${EquipmentSlotType.list()}" var="slotType">
        <tr>
            <td><label for="slotIt_${itemType.id}"><g:message code="${slotType.name}"/></label> </td>
            <g:set var="amount" value="${ RequiredSlot.findByItemTypeAndSlotType(itemType,slotType)?.amount ?: 0 }"/>
            <td><input type="text" id="slotIt_${itemType.id}" name="slotType_${slotType.id}" value="${amount}"></td>
        </tr>
    </g:each>
    <tr>
        <td id="updateResultRS_${itemType.id}">&nbsp;</td>
        <td><g:submitToRemote update="[success:'requiredSlots_'+itemType.id, failure:'updateResult_'+itemType.id]"
            url="[controller:'itemAdmin', action:'updateRequiredSlots']"
            value="${message(code:'slots.save')}"
        /></td>
    </tr>
    </tbody>
</table>
</g:form>