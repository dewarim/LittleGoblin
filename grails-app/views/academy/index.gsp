<!DOCTYPE HTML>
<html>
<head>

	<g:render template="/shared/header"/>	
	
</head>
<body class=" main ">
<g:render template="/shared/logo"/>
    <div class="navigation">
        <g:link controller="town" action="show" params="[pc:pc.id]"><g:message code="link.to.town"/></g:link>
    </div>
<div class="colmask ">
	<div class="col1">

		<div id="inventory" class="inventory">
			<g:render template="/shared/sideInventory"/>
		</div>

	</div>
	<div class="col2">
		<div class="pc_main">

			<h1><g:message code="academy.list.h"/></h1>

			<div id="message" class="message">
				<g:if test="${flash.message}">${flash.message}</g:if>
			</div>

			<div id="academy_intro" class="description">
				<g:message code="academy.list.description"/>
			</div>
			<br>
			<div id="academy_list_all">
				<g:render template="/academy/academy_list"
						model="[academies:academies, pc:pc, academyCount:academyCount, max:max, offset:offset]"/>
			</div>

		</div>
	</div>
	<div class="col3">
		<g:render template="/shared/player_character" model="[showEquipment:true]"/>
	</div>
</div>
<g:render template="/shared/footer"/>
</body>
</html>