<!DOCTYPE HTML>
<html>
<head>

	<meta name="layout" content="main"/>
   	

</head>
<body class="main">
<g:render template="/shared/logo"/>
    <div class="navigation">
        <g:link controller="portal" action="landing"><g:message code="link.to.home"/></g:link>
        <g:link controller="admin" action="index"><g:message code="link.to.admin"/></g:link>
    </div>
<div class="colmask ">

	<div class="full_col2">

		<h1><g:message code="mob.admin.headline"/></h1>
		<div id="message" class="message"><g:if test="${flash.message}">${flash.message}</g:if>
		</div>
		<div class="intro">
			<p>
				<g:message code="mob.admin.intro"/>
			</p>
		</div>
		<div class="mob_create type_create">
			<g:render template="/mobAdmin/create"/>
		</div>
		<h2><g:message code="mob.admin.list"/></h2>
		<div class="mob_list type_list" id="mobList">
			<g:render template="/mobAdmin/list" model="[mobs:mobs]"/>
		</div>
	</div>

</div>
<g:render template="/shared/footer"/>
</body>
</html>