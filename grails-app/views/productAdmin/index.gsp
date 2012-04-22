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
        <g:link controller="productCategoryAdmin" action="index"><g:message code="link.to.productCategoryAdmin"/> </g:link>
    </div>
<div class="colmask ">

	<div class="full_col2">

		<h1><g:message code="product.admin.headline" /></h1>
		<div id="message" class="message"><g:if test="${flash.message}">${flash.message}</g:if>
		</div>
		<div class="intro">
			<g:message code="product.admin.intro"/>
		</div>
		<div class="product_create type_create">
			<g:render template="/productAdmin/create" />
		</div>
		<h2><g:message code="product.admin.list"/> </h2>
		<div class="product_list type_list" id="productList">
			<g:render template="/productAdmin/list" model="[products:products]"/>
		</div>
	</div>

</div>
<g:render template="/shared/footer"/>
</body>
</html>