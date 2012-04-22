<!DOCTYPE HTML>
<html>
<head>

	<g:render template="/shared/header"/>
	
</head>
<body class="main">
<g:render template="/shared/logo"/>
    <div class="navigation">
        <g:link controller="portal" action="landing"><g:message code="link.to.home"/></g:link>
        <g:link controller="admin" action="index"><g:message code="link.to.admin"/> </g:link>
        <g:link controller="categoryAdmin" action="index"><g:message code="link.to.categoryAdmin"/> </g:link>
        <g:link controller="shopOwnerAdmin" action="index"><g:message code="link.to.shopOwnerAdmin"/> </g:link>
	</div>
<div class="colmask ">

	<div class="full_col2">

		<h1><g:message code="shop.admin.headline" /></h1>
		<div id="message" class="message"><g:if test="${flash.message}">${flash.message}</g:if>
		</div>
		<div class="intro">
			<g:message code="shop.admin.intro"/>
		</div>
		<div class="shop_create type_create">
			<g:render template="/shopAdmin/create" />
		</div>
		<h2><g:message code="shop.admin.list"/> </h2>
		<div class="shop_list type_list" id="shopList">
			<g:render template="/shopAdmin/list" model="[shops:shops]"/>
		</div>
	</div>

</div>
<g:render template="/shared/footer"/>
</body>
</html>