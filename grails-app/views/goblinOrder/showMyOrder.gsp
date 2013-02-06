<!DOCTYPE HTML>
<html>
<head>

	<meta name="layout" content="main"/>
   	

</head>
<body class=" main ">
<g:render template="/shared/logo"/>
<div class="colmask ">
	<div class="col1">
		<div class="navigation">
			<g:link controller="town" action="show" params="[pc:pc.id]"><g:message code="link.to.town"/></g:link>
		</div>
		<div id="inventory" class="inventory">
			<g:render template="/shared/sideInventory"/>
		</div>
		
	</div>
	<div class="col2">
		<div class="pc_main">

			<h1><g:message code="${order.name}"/></h1>

			<div id="message" class="message">
				<g:if test="${flash.message}">${flash.message}</g:if>
			</div>

			<div id="order_description" class="description order_description">
				${order.description}
			</div>

			<table class="order_info_table" border="1">
				<tr>
					<td>
						<g:if test="${order.leader.male}">
							<g:message code="order.master"/>
						</g:if>
						<g:else>
							<g:message code="order.mistress"/>
						</g:else>
					</td>
					<td>${order.leader.name}</td>
				</tr>
				<tr>
					<td><g:message code="order.score"/></td>
					<td>${order.score}</td>
				</tr>
				<tr><td><g:message code="order.members.th"/></td>
					<td>${order.members?.size()}</td>
				</tr>
			</table>

			<br>
			<div id="chatterbox">
				<g:render template="/chatterBox/box" model="[currentBox:currentBox, pc:pc]"/>
			</div>

			### show common items ### <br>

			<div class="order_administration">
				<h2>Administration</h2>
				<ul>
					<g:if test="${! order.applications?.isEmpty()}">
					<li><g:link action="showApplications" controller="goblinOrder" params="[pc:pc.id]">
							<g:message code="order.applicants.link"/>
						</g:link>
					</li>
					</g:if> 
					<li>
						<g:link action="showMembers" controller="goblinOrder" params="[pc:pc.id]">
							<g:message code="order.members.link"/>
						</g:link>
					</li>
				</ul>
			</div>

			<br>
			<br>
			<g:form action="leave" controller="goblinOrder" onsubmit="return confirm('${message(code:'order.confirm.leave')}');">
				<input type="hidden" name="pc" value="${pc.id}">
				<g:submitButton name="leaveOrder" value='${message(code:"order.leave.button")}'/>
			</g:form>

		</div>
	</div>
	<div class="col3">
		<g:render template="/shared/player_character" model="[showEquipment:true]"/>
	</div>
</div>
</body>
</html>