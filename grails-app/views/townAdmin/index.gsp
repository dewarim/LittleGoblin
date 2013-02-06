<!DOCTYPE HTML>
<html>
<head>

	<meta name="layout" content="main"/>
   	
	
</head>
<body class="main">
<g:render template="/shared/logo"/>
    <div class="navigation">
        <g:link controller="portal" action="landing"><g:message code="link.to.home"/></g:link>
        <g:link controller="admin" action="index"><g:message code="link.to.admin"/> </g:link>
    </div>
<div class="colmask ">

	<div class="full_col2">

		<h1><g:message code="town.admin.headline" /></h1>
		<div id="message" class="message"><g:if test="${flash.message}">${flash.message}</g:if>
		</div>
		<div class="intro">
			<g:message code="town.admin.intro"/>
		</div>
		<div class="town_create type_create">
			<g:render template="/townAdmin/create" />
		</div>
		<h2><g:message code="town.admin.list"/> </h2>
		<div class="town_list type_list" id="townList">
			<g:render template="/townAdmin/list" model="[towns:towns]"/>
		</div>
	</div>

</div>

</body>
</html>