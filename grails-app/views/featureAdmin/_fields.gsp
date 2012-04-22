<input type="hidden" name="id" value="${feature?.id}"/>
<label><g:message code="feature.name"/></label><br>
<g:textField name="name" value="${feature?.name}"/>
<br>
<label><g:message code="feature.script"/></label><br>
<g:select from="${grailsApplication.config.featureScripts}" name="script" value="${feature?.script?.name}" />
<br>
<label><g:message code="feature.internalName"/></label><br>
<g:textField name="internalName" value="${feature?.internalName}"/>
<br>
