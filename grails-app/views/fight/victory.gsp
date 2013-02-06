<!DOCTYPE HTML>
<html>
<head>

	<meta name="layout" content="main"/>
   	

</head>
<body class="main">
<g:render template="/shared/logo"/>
    <div class="navigation">

	</div>
<div class="colmask ">
	<div class="col1">


	</div>
	<div class="col2">

		<h1><g:message code="fight.victory.title"/></h1>

		<g:if test="${flash.message}">
			<div class="message">${flash.message}</div>
		</g:if>
		<br/>
		<g:message code="fight.victory.gratz" args="${[message(code:mob.name)]}"/>

		<g:render template="mob_image" model="[image:mob?.image?.image]"/>

		<h2><g:message code="fight.victory.treasure"/></h2>
		<g:if test="${treasure?.size() > 0}">
			<ul>
				<g:each in="${treasure}" var="item">
					<li><g:message code="${item.type.name}"/></li>
				</g:each>
			</ul>
		</g:if>
		<g:else>
			<g:message code="fight.victory.no.treasure"/>
		</g:else>
		<g:render template="/shared/combat_messages"/>

		<g:if test="${quest.finished}">
			<g:message code="quest.default.finished"/>
			<br/>
			<g:link controller="town" action="show" params="[pc:pc.id]"><g:message code="link.to.town"/></g:link>
		</g:if>
		<g:else>
			<g:link controller="quest" action="continueQuest"><g:message code="quest.continue.link"/></g:link>
		</g:else>
		<br/>
        <br/>
        <br/>
	</div>
	<div class="col3">
		<g:render template="/shared/player_character" model="[showEquipment:true]"/>
	</div>
</div>

</body>
</html>