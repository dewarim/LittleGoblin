<!DOCTYPE HTML>
<html>
<head>
	<meta name="layout" content="main"/>
   	
</head>
<body class="main">
<g:render template="/shared/logo"/>
<div class="navigation"><g:link controller="portal" action="landing"><g:message code="link.to.home"/></g:link>
			<g:if test="${user.checkRole('ROLE_ADMIN')}">
				<g:link controller="admin" action="index"><g:message code="link.to.admin"/></g:link>
			</g:if></div>

<div class="colmask ">

	<div class="full_col2">

		<h1><g:message code="welcome" args="${[user.username]}"/></h1>
		<div id="message" class="message"><g:if test="${flash.message}">${flash.message}</g:if>
		</div>
		<br>

		<table>
			<tr>
				<th><g:message code="pc.name"/></th>
				<th><g:message code="pc.xp"/></th>
				<th><g:message code="pc.alive"/></th>
			</tr>
			<g:each in="${chars}" var="c">
				<tr>
					<td class="center">
						<g:link action="show" controller="town" params="[pc:c.id]">${c.name}</g:link>
					</td>
					<td class="center">${c.xp}</td>
					<td class="center">${c.alive ? message(code: 'pc.is_alive') : message(code: 'pc.is_dead')}</td>
				</tr>
			</g:each>
		</table>
		<p><g:message code="start.choose.character"/> </p>
		<hr>
		<div class="create_character">
			<h2><g:message code="pc.create.headline"/> </h2>
			<g:if test="${createError}">
				<div class="message"><g:message code="${createError}"/></div>
			</g:if>
			<g:form action="save" controller="playerCharacter" method="POST">
				<g:message code="character.name"/> &nbsp; <g:textField name="name" size="30" maxlength="30"/>
				<g:submitButton name="createSubmit" value="${message(code:'pc.create.button')}"/>
			</g:form>
		</div>
	</div>

</div>
<g:render template="/shared/footer"/>
</body>
</html>