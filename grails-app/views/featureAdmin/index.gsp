<!DOCTYPE HTML>
<html>
<head>

	<meta name="layout" content="main"/>
   	
	
</head>
<body class="main">

       <div class="navigation">
            <g:link controller="portal" action="landing"><g:message code="link.to.home"/></g:link>
            <g:link controller="admin" action="index"><g:message code="link.to.admin"/> </g:link>
       </div>
<div class="colmask ">

	<div class="full_col2">

		<h1><g:message code="feature.admin.headline" /></h1>
		<div id="message" class="message"><g:if test="${flash.message}">${flash.message}</g:if>
		</div>
		<div class="intro">
			<g:message code="feature.admin.intro"/>
		</div>
		<div class="feature_create type_create">
			<g:render template="/featureAdmin/create" />
		</div>
		<h2><g:message code="feature.admin.list"/> </h2>
		<div class="feature_list type_list" id="featureList">
			<g:render template="/featureAdmin/list" model="[features:features]"/>
		</div>
	</div>

</div>

</body>
</html>