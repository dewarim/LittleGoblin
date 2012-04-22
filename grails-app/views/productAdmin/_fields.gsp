<%@ page import="de.dewarim.goblin.pc.crafting.ProductCategory;" %>
<input type="hidden" name="id" value="${product?.id}"/>
<label><g:message code="product.name"/> </label><br>
<g:textField name="name" value="${product?.name}"/>
<br>
<label><g:message code="product.timeNeeded"/> </label><br>
<g:textField name="timeNeeded" value="${product?.timeNeeded}"/>
<br>
<label><g:message code="product.category"/> </label><br>
<g:select from="${ProductCategory.listOrderByName()}" name="category" optionValue="${{message(code:it.name)}}" optionKey="id" value="${product?.category?.id}"/>

