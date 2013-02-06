<!DOCTYPE HTML>
<html>
<head>

	<meta name="layout" content="main"/>
   	


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
		<div class="mail">

			<h1 class="mail"><g:message code="mail.write.h"/></h1>

			<div id="message" class="message">
				<g:if test="${flash.message}">${flash.message}</g:if>

			</div>

			<div class="error_messages">
				<ul>
				<g:eachError bean="${mail}">
					<li><g:message error="${it}"/></li>
				</g:eachError>
				</ul>
			</div>

			<div id="new_message" class="new_message_form">
				<g:form action="sendMail" controller="mailBox">
					<table>
						<tr>
							<td><g:message code="mail.to"/> </td>
							<td>
								<g:textField name="recipientName" value="${mail?.recipient?.name}"/>
							</td>
						</tr>
						<tr>
							<td><g:message code="mail.subject"/> </td>
							<td>
								<g:textField name="subject" value="${mail?.subject}"/><br>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<g:textArea name="content" value="${mail?.content}" rows="10" cols="60"/><br>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>
								<g:submitButton name="newMessageSubmit"	value="${message(code:'mail.new.submit')}"/>
							</td>
						</tr>
					</table>
				</g:form>
			</div>

		</div>
	</div>
	<div class="col3">
		<g:render template="/shared/player_character" model="[showEquipment:true]"/>
	</div>
</div>

</body>
</html>