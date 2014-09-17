<!DOCTYPE HTML>
<html>
<head>

	<meta name="layout" content="main"/>
   	

</head>
<body class=" main ">

<div class="colmask ">
	<div class="col1">
		<div class="navigation">
			<g:link controller="town" action="show" params="[pc:pc.id]"><g:message code="link.to.town"/></g:link>
			<br>
			<g:link controller="goblinOrder" action="showMyOrder" params="[pc:pc.id, order:order.id]">
				<g:message code="order.main.link"/>
			</g:link>
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

			<h2><g:message code="order.current.applications"/>  </h2>
			<g:if test="${order.applications.isEmpty()}">
			    <g:message code="order.no_applicants"/>
		    </g:if>
			<g:else>
			<table class="applicants" border="1">
				<tr>
					<th><g:message code="applicant.name"/></th>
					<th><g:message code="applicant.level"/></th>
					<th><g:message code="applicant.text"/></th>
					<th colspan="2"><g:message code="applicant.decide"/></th>
				</tr>
				<g:each in="${order.applications}" var="application">
					<g:set var="applicationNodeId" value="application_${application.id}"/>
					<tr id="${applicationNodeId}">
						<td>${application.applicant.name}</td>
						<td>${application.applicant.level}</td>
						<td>${application.applicationMessage}</td>
						<td class="application_cell">
								<g:form action="acceptApplication" controller="goblinOrder">
									<input type="hidden" name="application" value="${application.id}">
									 <g:submitToRemote update="[success:'message', failure:'message']" onSuccess="hideRow('${applicationNodeId}');" value="${message(code:'order.accept.applicant')}" url="[action:'acceptApplication', controller:'goblinOrder']"/>
								</g:form>
						</td>
						<td class="application_cell">
								<g:form>
									<input type="hidden" name="application" value="${application.id}">
									<g:message code="order.deny.reason"/>: <g:textField name="reason" value="// TODO: implement this."/><br>
									 <g:submitToRemote update="[success:'message', failure:'message']" onSuccess="hideRow('${applicationNodeId}');" value="${message(code:'order.deny.applicant')}" url="[action:'denyApplication', controller:'goblinOrder']"/>
								</g:form>
						</td>
					</tr>
				</g:each>
			</table>
			</g:else>
		</div>
	</div>
	<div class="col3">
		<g:render template="/shared/player_character" model="[showEquipment:true]"/>
	</div>
</div>
</body>
</html>