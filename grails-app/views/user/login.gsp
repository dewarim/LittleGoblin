<g:render template="/shared/header"/>

<body class="main">

<h1><g:message code="login.title"/></h1>
<g:if test="${flash.message}">
	<div class="message">${flash.message}</div>
</g:if><br/>
<g:render template="/shared/login"/>

<g:render template="/shared/footer"/>