<%@ page import="de.dewarim.goblin.License; de.dewarim.goblin.Artist" %>
<input type="hidden" name="id" value="${image?.id}"/>
<label><g:message code="image.name"/> </label><br>
<g:textField name="name" value="${image?.name}"/>
<br>
<label><g:message code="image.description"/> </label><br>
<g:textArea name="description" rows="6" cols="60" value="${image?.description}"/>
<br>
<label><g:message code="image.sourceUrl"/> </label><br>
<g:textField name="sourceUrl" value="${image?.sourceUrl}"/>
<br>
<label><g:message code="image.url"/> </label><br>
<g:textField name="url" value="${image?.url}"/>
<br>
<label><g:message code="image.height"/> </label><br>
<g:textField name="height" value="${image?.height ?: 64}"/>
<br>
<label><g:message code="image.width"/> </label><br>
<g:textField name="width" value="${image?.width ?: 64}"/>
<br>
<label><g:message code="image.artist"/> </label><br>
<g:select from="${Artist.listOrderByName()}" optionValue="name" optionKey="id" name="artist" value="${image?.artist?.id}"/>
<br>
<label><g:message code="image.license"/> </label><br>
<g:select from="${License.listOrderByName()}" optionValue="name" optionKey="id" name="license" value="${image?.license?.id}"/>
<br>