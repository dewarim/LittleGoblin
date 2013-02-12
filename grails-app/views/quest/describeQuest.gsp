<!DOCTYPE HTML>
<html>
<head>
    <meta name="layout" content="main"/>
	
</head>
<body class=" main quest ">

     <div class="navigation">
		    <g:link controller="town" action="show" params="[pc:pc.id]"><g:message code="link.to.town"/></g:link>
	 </div>
<div class="colmask ">
			<div class="col1">


            </div>
			<div class="col2">

<h1><g:message code="${qt.name}"/></h1>
<g:if test="${flash.message}">
	<div class="message">${flash.message}</div>
</g:if>
<div class=" description quest_text ">
	<g:message code="${qt.description}"/>
</div>
<div class=" decision start_quest">
<g:message code="quest.accept"/> <g:link action="startQuest" controller="quest" params="[quest:qt.id, pc:pc.id]">
		<g:message code="${qt.name}"/></g:link>
</div>
<div class="decision decline_quest">
	<g:link action="show" controller="town" params="[pc:pc.id]">
		<g:message code="quest.decline"/>
	</g:link>
</div>
			</div>
							<div class="col3">
	<g:render template="/shared/player_character" model="[showEquipment:true]"/>
				</div>
</div>

</body>
</html>