<%@ page import="de.dewarim.goblin.shop.Shop; de.dewarim.goblin.Dice;" %>
<input type="hidden" name="id" value="${shopOwner?.id}"/>
<label><g:message code="shopOwner.name"/> </label><br>
<g:textField name="name" value="${shopOwner?.name}"/>
<br>
<label><g:message code="shopOwner.description"/> </label><br>
<g:textArea name="description" rows="6" cols="60" value="${shopOwner?.description}"/>
<br>
<label><g:message code="shopOwner.priceModifierDice"/> </label><br>
<g:select from="${Dice.listOrderByName()}" optionValue="${{it.name + ' // '+it.toString()}}" optionKey="id" name="priceModifierDice" value="${shopOwner?.priceModifierDice?.id}"/>
<br>
<label><g:message code="shopOwner.shops"/> </label><br>
<g:select from="${Shop.listOrderByName()}" optionValue="name" optionKey="id" name="shops" multiple="true" value="${shopOwner?.shops*.id}"/>
<br>