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

		<h1><g:message code="equipmentSlotType.headline" /></h1>
		<div id="message" class="message"><g:if test="${flash.message}">${flash.message}</g:if>
		</div>
		<div class="intro">
			<g:message code="equipmentSlotType.intro"/>
		</div>
		<div class="equipment_slot_type_create type_create">
			<g:render template="/equipmentSlotTypeAdmin/create" />
		</div>
		<div class="equipment_slot_type_list type_list">
			<ul id="equipmentSlotTypeTypeList">
				<g:render template="/equipmentSlotTypeAdmin/list" model="[slotList:slotList]"/>
			</ul>
		</div>
	</div>

</div>
<g:render template="/shared/footer"/>
</body>
</html>