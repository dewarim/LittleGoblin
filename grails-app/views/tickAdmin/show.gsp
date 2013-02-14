<%@ page import="de.dewarim.goblin.ticks.Tick" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'tick.label', default: 'Tick')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>    
		<a href="#show-tick" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-tick" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list tick">
			
				<g:if test="${tick?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="name" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${tick}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${tick?.beanName}">
				<li class="fieldcontain">
					<span id="beanName-label" class="property-label"><g:message code="tick.beanName.label" default="Bean Name" /></span>
					
						<span class="property-value" aria-labelledby="beanName-label"><g:fieldValue bean="${tick}" field="beanName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${tick?.tickLength}">
				<li class="fieldcontain">
					<span id="tickLength-label" class="property-label"><g:message code="tick.tickLength.label" default="Tick Length" /></span>
					
						<span class="property-value" aria-labelledby="tickLength-label"><g:fieldValue bean="${tick}" field="tickLength"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${tick?.active}">
				<li class="fieldcontain">
					<span id="active-label" class="property-label"><g:message code="tick.active.label" default="Active" /></span>
					
						<span class="property-value" aria-labelledby="active-label"><g:formatBoolean boolean="${tick?.active}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${tick?.currentTick}">
				<li class="fieldcontain">
					<span id="currentTick-label" class="property-label"><g:message code="tick.currentTick.label" default="Current Tick" /></span>
					
						<span class="property-value" aria-labelledby="currentTick-label"><g:fieldValue bean="${tick}" field="currentTick"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${tick?.id}" />
					<g:link class="edit" action="edit" id="${tick?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
