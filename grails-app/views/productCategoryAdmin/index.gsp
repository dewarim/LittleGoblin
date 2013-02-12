<!DOCTYPE HTML>
<html>
<head>

	<meta name="layout" content="main"/>
   	
	
</head>
<body class="main">

    <div class="navigation">
        <g:link controller="portal" action="landing"><g:message code="link.to.home"/></g:link>
        <g:link controller="admin" action="index"><g:message code="link.to.admin"/> </g:link>
        <g:link controller="productAdmin" action="index"><g:message code="link.to.productAdmin"/> </g:link>
    </div>
<div class="colmask ">

	<div class="full_col2">

		<h1><g:message code="category.admin.headline" /></h1>
		<div id="message" class="message"><g:if test="${flash.message}">${flash.message}</g:if>
		</div>
		<div class="intro">
			<g:message code="category.admin.intro"/>
		</div>
		<div class="category_create type_create">
			<g:render template="/productCategoryAdmin/create" />
		</div>
		<h2><g:message code="category.admin.list"/> </h2>
		<div class="category_list type_list" id="categoryList">
			<g:render template="/productCategoryAdmin/list" model="[categories:categories]"/>
		</div>
	</div>

</div>

</body>
</html>