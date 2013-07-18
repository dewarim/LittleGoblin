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

		<h1><g:message code="goblinOrder.admin.headline" /></h1>
		<div id="message" class="message"><g:if test="${flash.message}">${flash.message}</g:if>
		</div>
		<div class="intro">
			<g:message code="goblinOrder.admin.intro"/>
		</div>
		<div class="goblinOrder_create type_create">
			<g:render template="/goblinOrderAdmin/create" />
		</div>
		<h2><g:message code="goblinOrder.admin.list"/> </h2>
		<div class="goblinOrder_list type_list" id="goblinOrderList">
			<g:render template="/goblinOrderAdmin/list" model="[goblinOrders:goblinOrders]"/>
		</div>
	</div>

</div>

</body>
</html>