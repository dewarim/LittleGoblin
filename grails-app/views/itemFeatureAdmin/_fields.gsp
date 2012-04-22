<%@ page import="de.dewarim.goblin.Feature; de.dewarim.goblin.item.ItemType" %>
<input type="hidden" name="id" value="${itemFeature?.id}"/>
<label><g:message code="itemFeature.feature"/></label><br>
<g:select from="${Feature.list()}" name="feature" value="${itemFeature?.feature}"
  optionValue="name" optionKey="id"
/>
<br>
<label><g:message code="itemFeature.itemType"/></label><br>
<g:select from="${ItemType.list()}" name="itemType" value="${itemFeature?.itemType}"
          optionValue="name" optionKey="id"
/>
<br>
<label><g:message code="itemFeature.config"/></label><br>
<g:textArea name="config" cols="60" rows="8" value="${itemFeature?.config ?: '<config />'}"/>
<br>
