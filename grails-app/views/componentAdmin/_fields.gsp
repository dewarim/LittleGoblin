<%@ page import="de.dewarim.goblin.ComponentType; de.dewarim.goblin.item.ItemType" %>
<input type="hidden" name="product" value="${product.id}">
<input type="hidden" name="id" value="${component?.id}">
<label for="amount_${component?.id}"><g:message code="component.amount"/></label>
<g:textField name="amount" value="${component?.amount}" id="amount_${component?.id}"/>
<label for="itemType_${component?.id}"><g:message code="component.itemType"/></label>
<g:select from="${ItemType.listOrderByName()}" name="itemType" optionValue="${{message(code:it.name)}}" optionKey="id" value="${component?.itemType?.id}"/>

<label for="type${component?.id}"><g:message code="component.itemType"/></label>
<g:select from="${ComponentType.values().sort{it}}" name="type" optionValue="${{message(code:it.toString())}}" optionKey="${{it.toString()}}" value="${component?.type}"/>

