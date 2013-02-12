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


<h1><g:message code="fight.flee.title"/></h1>

<g:if test="${flash.message}">
	<div class="message">${flash.message}</div>
</g:if>

<div class="quest_fail">
    <g:message code="flee.quest.fail"/>
    "<g:message code="${quest.template.name}"/>"
</div>

<div class="nav">
    <g:link action="show" controller="town" params="[pc:pc.id]"><g:message code="flee.link.to.town"/></g:link>
</div>

</div>
	<div class="col3">
				   <g:render template="/shared/player_character" model="[showEquipment:true]"/>
	</div>
</div>	

<g:render template="/shared/footer" />
</body>
</html>