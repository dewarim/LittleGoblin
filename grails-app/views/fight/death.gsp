<!DOCTYPE HTML>
<html>
<head>
	<meta name="layout" content="main"/>
   	

	
</head>
<body class="main">

    <div class="navigation">

	</div>
<div class="colmask ">
	<div class="col1">


	</div>
	<div class="col2">

		<h1><g:message code="fight.death.title" args="${[pc.name]}"/></h1>

		<g:if test="${flash.message}">
			<div class="message">${flash.message}</div>
		</g:if>

		<g:render template="/shared/combat_messages"/>

		<div class="quest_fail">
			<g:message code="death.quest.fail"/>
			"<g:message code="${quest.template.name}"/>"
		</div>
		<div class="start_link">
			<g:link action="start" controller="portal"><g:message code="user.link.start"/></g:link>
		</div>
	</div>
	<div class="col3">
		<g:render template="/shared/player_character" model="[showEquipment:true]"/>
	</div>
</div>
<g:render template="/shared/footer" />
</body>
</html>