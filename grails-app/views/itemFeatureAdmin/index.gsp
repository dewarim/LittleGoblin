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

		<h1><g:message code="itemFeature.admin.headline" /></h1>
		<div id="message" class="message"><g:if test="${flash.message}">${flash.message}</g:if>
		</div>
		<div class="intro">
			<g:message code="itemFeature.admin.intro"/>
		</div>
		<div class="itemFeature_create type_create">
			<g:render template="/itemFeatureAdmin/create"/>
		</div>
		<h2><g:message code="itemFeature.admin.list"/> </h2>
		<div class="itemFeature_list type_list" id="itemFeatureList">
			<g:render template="/itemFeatureAdmin/list" model="[itemFeatures:itemFeatures]"/>
		</div>
	</div>

</div>

</body>
</html>