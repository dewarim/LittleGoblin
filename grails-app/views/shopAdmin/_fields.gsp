<%@ page import="de.dewarim.goblin.town.Town; de.dewarim.goblin.shop.ShopOwner;" %>
<input type="hidden" name="id" value="${shop?.id}"/>
<label><g:message code="name"/> </label><br>
<g:textField name="name" value="${shop?.name}"/>
<br>
<label><g:message code="description"/> </label><br>
<g:textArea name="description" rows="6" cols="60" value="${shop?.description}"/>
<br>
<label><g:message code="shop.owner"/> </label><br>
<g:select from="${ShopOwner.listOrderByName()}" name="owner"  optionValue="${{message(code:it.name)}}" optionKey="id" value="${shop?.owner?.id}"/>
<br>
<label><g:message code="shop.town"/> </label><br>
<g:select from="${Town.listOrderByName()}" name="town"  optionValue="${{message(code:it.name)}}" optionKey="id" value="${shop?.town?.id}"/>
<br>


