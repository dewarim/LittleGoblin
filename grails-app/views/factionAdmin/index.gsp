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

		<h1><g:message code="faction.admin.headline" /></h1>
		<div id="message" class="message"><g:if test="${flash.message}">${flash.message}</g:if>
		</div>
		<div class="intro">
			<g:message code="faction.admin.intro"/>
		</div>
		<div class="faction_create type_create">
			<g:render template="/factionAdmin/create" />
		</div>
		<h2><g:message code="faction.admin.list"/> </h2>
		<div class="faction_list type_list" id="factionList">
			<g:render template="/factionAdmin/list" model="[factions:factions]"/>
		</div>
	</div>

</div>

</body>
</html>