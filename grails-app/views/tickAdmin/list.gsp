
<%@ page import="de.dewarim.goblin.ticks.Tick" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'tick.label', default: 'Tick')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-tick" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-tick" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'tick.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="beanName" title="${message(code: 'tick.beanName.label', default: 'Bean Name')}" />
					
						<g:sortableColumn property="tickLength" title="${message(code: 'tick.tickLength.label', default: 'Tick Length')}" />
					
						<g:sortableColumn property="active" title="${message(code: 'tick.active.label', default: 'Active')}" />
					
						<g:sortableColumn property="currentTick" title="${message(code: 'tick.currentTick.label', default: 'Current Tick')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${tickList}" status="i" var="tick">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${tick.id}">${fieldValue(bean: tick, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: tick, field: "beanName")}</td>
					
						<td>${fieldValue(bean: tick, field: "tickLength")}</td>
					
						<td><g:formatBoolean boolean="${tick.active}" /></td>
					
						<td>${fieldValue(bean: tick, field: "currentTick")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${tickTotal}" />
			</div>
		</div>
	</body>
</html>
