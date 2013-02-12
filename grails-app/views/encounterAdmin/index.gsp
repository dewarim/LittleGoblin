<!DOCTYPE HTML>
<html>
<head>

	<meta name="layout" content="main"/>
   	

</head>
<body class="main">

     <div class="navigation">
        <g:link controller="portal" action="landing"><g:message code="link.to.home"/></g:link>
        <g:link controller="admin" action="index"><g:message code="link.to.admin"/></g:link>
        <g:link controller="questAdmin" action="index"><g:message code="link.to.questAdmin"/></g:link>
     </div>
<div class="colmask ">

	<div class="full_col2">

		<h1><g:message code="encounter.admin.headline"/></h1>
		<div id="message" class="message"><g:if test="${flash.message}">${flash.message}</g:if>
		</div>
		<div class="intro">
			<p>
				<g:message code="encounter.admin.intro"/>
			</p>
			<p>
				<g:message code="encounter.admin.intro2"/>
			</p>
		</div>
		<div class="encounter_create type_create">
			<g:render template="/encounterAdmin/create"/>
		</div>
		<h2><g:message code="encounter.admin.list"/></h2>
		<div class="encounter_list type_list" id="encounterList">
			<g:render template="/encounterAdmin/list" model="[encounters:encounters]"/>
		</div>
	</div>

</div>

</body>
</html>