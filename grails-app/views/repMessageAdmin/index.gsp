<!DOCTYPE HTML>
<html>
<head>

	<meta name="layout" content="main"/>
   	
	
</head>
<body class="main">

    <div class="navigation">
        <g:link controller="portal" action="landing"><g:message code="link.to.home"/></g:link>
        <g:link controller="admin" action="index"><g:message code="link.to.admin"/> </g:link>
        <g:link controller="rmmAdmin" action="index"><g:message code="link.to.rmmAdmin"/> </g:link>
    </div>
<div class="colmask ">
	<div class="col1">


	</div>
	<div class="col2">

		<h1><g:message code="repMessage.headline" args="[rmm.name]"/></h1>
		<div id="message" class="message"><g:if test="${flash.message}">${flash.message}</g:if>
		</div>
		<div class="intro">
			<g:message code="repMessage.intro" args="[rmm.name]"/>
		</div>
		<div class="repMessage_create type_create">
			<g:render template="/repMessageAdmin/create" />
		</div>
		<h2><g:message code="repMessage.list"/> </h2>
		<div class="repMessage_list type_list" id="repMessageList">
			<g:render template="list" model="[rmm:rmm, repMessage:repMessage]"/>
		</div>
	</div>
</div>

</body>
</html>