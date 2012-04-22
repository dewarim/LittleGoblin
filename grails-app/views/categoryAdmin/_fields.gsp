<%@ page import="de.dewarim.goblin.item.ItemType; de.dewarim.goblin.shop.Shop" %>
<input type="hidden" name="id" value="${category?.id}"/>
<label><g:message code="category.name"/> </label><br>
<g:textField name="name" value="${category?.name}"/>
<br>
<label><g:message code="category.shops"/> </label><br>
<g:select from="${Shop.list()}" name="shops" value="${selectedShops*.id}"
		multiple="true" noSelection="${['null':'---']}" size="10"
		optionKey="id" optionValue="name"/>
<br>
<label><g:message code="category.itemTypes"/> </label><br>
<g:select from="${ItemType.list()}" name="itemTypes" value="${selectedItemTypes*.id}"
		multiple="true" noSelection="${['null':'---']}" size="10"
		optionKey="id" optionValue="name"/>
<br>
