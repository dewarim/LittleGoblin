<!DOCTYPE HTML>
<html>
<head>

	<meta name="layout" content="main"/>
   	

	
</head>
<body class=" main quest ">
<g:render template="/shared/logo"/>
    <div class="navigation">
    </div>
<div class="colmask ">
	<div class="col1">


	</div>
	<div class="col2">
		<h1><g:message code="${quest.template.name}"/></h1>
		<g:if test="${flash.message}">
			<div class="message">${flash.message}</div>
		</g:if>
		<div class=" description quest_text ">
			<g:message code="${quest.template.description}"/>
		</div>
		<div class=" decision continue_quest">
			<g:link action="continueQuest" controller="quest" params="[quest:quest.id, pc:pc.id]">
				<g:message code="quest.continue.link"/></g:link>
		</div>
	</div>

	<div class="col3">
		<g:render template="/shared/player_character" model="[showEquipment:true]"/>
	</div>
</div>

</body>
</html>