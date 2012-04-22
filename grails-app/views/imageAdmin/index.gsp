<!DOCTYPE HTML>
<html>
<head>

	<g:render template="/shared/header"/>

</head>
<body class="main">
<g:render template="/shared/logo"/>
    <div class="navigation">
        <g:link controller="portal" action="landing"><g:message code="link.to.home"/></g:link>
        <g:link controller="admin" action="index"><g:message code="link.to.admin"/></g:link>
    </div>
<div class="colmask ">

	<div class="full_col2">

		<h1><g:message code="image.admin.headline"/></h1>
		<div id="message" class="message"><g:if test="${flash.message}">${flash.message}</g:if>
		</div>
		<div class="intro">
			<g:message code="image.admin.intro"/>
		</div>
		<div class="image_create type_create">
			<g:render template="/imageAdmin/create"/>
		</div>
		<h2><g:message code="image.admin.list"/></h2>
		<div class="image_list type_list" id="imageList">
			<g:render template="/imageAdmin/list" model="[images:images]"/>
		</div>
	</div>

</div>
<g:render template="/shared/footer"/>
</body>
</html>