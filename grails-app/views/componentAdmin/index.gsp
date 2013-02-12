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
	<div class="col1">


	</div>
	<div class="col2">

		<h1><g:message code="component.headline" args="[product.name]"/></h1>
		<div id="message" class="message"><g:if test="${flash.message}">${flash.message}</g:if>
		</div>
		<div class="intro">
			<g:message code="component.intro" args="[product.name]"/>
		</div>
		<div class="component_create type_create">
			<g:render template="/componentAdmin/create" />
		</div>
		<h2><g:message code="component.list"/> </h2>
		<div class="component_list type_list" id="componentList">
			<g:render template="list" model="[product:product, component:component]"/>
		</div>
	</div>
</div>

</body>
</html>