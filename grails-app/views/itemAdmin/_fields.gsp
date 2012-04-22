<%@ page import="de.dewarim.goblin.Dice" %><g:set var="iid" value="${itemType?.id}"/>
<tr>
    <td><label for="name_${iid}"><g:message code="name"/></label></td>
    <td><g:textField id="name_${iid}" name="name" value="${itemType?.name}"/></td>
</tr>
<tr>
    <td><label for="description_${iid}"><g:message code="description"/></label></td>
    <td><g:textArea id="description_${iid}" name="description" rows="6" cols="60"
                    value="${itemType?.description}"/></td>
</tr>
<tr>
    <td><label for="usable_${iid}"><g:message code="itemType.usable"/></label></td>
    <td><g:checkBox name="usable" id="usable_${iid}" value="${itemType?.usable}"/></td>
</tr>
<tr>
    <td><label for="uses_${iid}"><g:message code="itemType.uses"/></label></td>
    <td><g:textField id="uses_${iid}" name="uses" value="${itemType?.uses ?: 1}"/></td>
</tr>
<tr>
    <td><label for="rechargeable_${iid}"><g:message code="itemType.rechargeable"/></label></td>
    <td><g:checkBox id="rechargeable_${iid}" name="rechargeable" value="${itemType?.rechargeable}"/></td>
</tr>
<tr>
    <td><label for="stackable_${iid}"><g:message code="itemType.stackable"/></label></td>
    <td><g:checkBox id="stackable_${iid}" name="stackable" value="${itemType?.stackable}"/></td>
</tr>
<tr>
    <td><label for="baseValue_${iid}"><g:message code="itemType.baseValue"/></label></td>
    <td><g:textField id="baseValue_${iid}" name="baseValue" value="${itemType?.baseValue ?: 0}"/></td>
</tr>
<tr>
    <td><label for="availability_${iid}"><g:message code="itemType.availability"/></label></td>
    <td><g:textField id="availability_${iid}" name="availability" value="${itemType?.availability ?: 0}"/></td>
</tr>
<tr>
    <td><label for="packageSize_${iid}"><g:message code="itemType.packageSize"/></label></td>
    <td><g:textField id="packageSize_${iid}" name="packageSize" value="${itemType?.packageSize ?: 1}"/></td>
</tr>
<tr>
    <td><label for="combatDice_${iid}"><g:message code="itemType.combatDice"/></label></td>
    <td><g:select id="combatDice_${iid}" from="${Dice.listOrderByName()}" name="combatDice"
                  optionKey="id" optionValue="${{it.toString() +' :: '+message(code:it.name) }}"
                  noSelection="${[null:'---']}"
                  value="${itemType?.combatDice?.id}"/></td>
</tr>
