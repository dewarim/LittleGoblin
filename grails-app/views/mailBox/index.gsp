<!DOCTYPE HTML>
<html>
<head>

	<meta name="layout" content="main"/>
   	


</head>
<body class=" main ">

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
		<div class="mail">

			<h1 class="mail"><g:message code="mail.h"/></h1>

			<div id="message" class="message">
				<g:if test="${flash.message}">${flash.message}</g:if>
			</div>

			<div id="box_view" class="box_view">
				<g:render template="/mailBox/mailbox" model="['mailBox':mailBox]"/>
			</div>

			<div id="newMailLink">
				<g:link action="writeMail" controller="mailBox">
					<g:message code="mail.write.link"/>
				</g:link>
			</div>

		</div>
	</div>
	<div class="col3">
		<g:render template="/shared/player_character" model="[showEquipment:true]"/>
	</div>
</div>

</body>
</html>