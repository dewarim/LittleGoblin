<!DOCTYPE HTML>
<html>
<head>

	<meta name="layout" content="main"/>
   	
	
</head>
<body class="main">
<g:render template="/shared/logo"/>

<div class="colmask ">
	<div class="col1">
		<div class="navigation">
			<g:link controller="portal" action="landing"><g:message code="link.to.home"/></g:link><br>			
			<g:link controller="admin" action="index"><g:message code="link.to.admin"/> </g:link><br>
			<g:link controller="productAdmin" action="index"><g:message code="link.to.productAdmin"/> </g:link>
		</div>
		<g:render template="/shared/footer"/>
	</div>
	<div class="col2">

		<h1><g:message code="requirement.headline" args="[product.name]"/></h1>
		<div id="message" class="message"><g:if test="${flash.message}">${flash.message}</g:if>
		</div>
		<div class="intro">
			<g:message code="requirement.intro" args="[product.name]"/>
		</div>
		<div class="requirement_create type_create">
			<g:render template="/skillRequirementAdmin/create" />
		</div>
		<h2><g:message code="requirement.list"/> </h2>
		<div class="requirement_list type_list" id="requirementList">
			<g:render template="list" model="[product:product, requirement:requirement]"/>
		</div>
	</div>
</div>
</body>
</html>