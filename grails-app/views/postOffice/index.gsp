<!DOCTYPE HTML>
<html>
<head>

	<meta name="layout" content="main"/>
   	

</head>

<body class=" main shop ">
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
		<div class="shop">
			<h1><g:message code="post.title"/></h1>

			<div id="message" class="message">
				<g:if test="${flash.message}">${flash.message}</g:if>
			</div>

			<div class=" quotation spacer1">
				<span class="citation">
					<g:message code="post.inscription"/>
				</span>
				&nbsp;
				<span class="attribution">
					<g:message code="post.inscription.attribute"/>
				</span>
			</div>
			<div class="clear spacer2"> </div>

			<div class="postOffice description">
				<g:message code="post.description"/>
			</div>
			<div class="spacer1">&nbsp;</div>
			<g:if test="${items?.size() > 0}">

				<div id="sendItem" class="send_item">
					<br>
					<g:form name="send_item_form">
						<div id="postError"></div>
						<label class="post_office" for="recipient"><g:message code="post.recipient.label"/></label>
						<g:textField id="recipient" name="recipient"/>
						<div id="itemList">
							<g:render template="itemList" model="[items:items]"/>
						</div>
						<label class="post_office" for="amount"><g:message code="post.amount"/> </label>
						<g:textField name="amount" value="1"/>
						<g:submitToRemote url="[action:'sendItem', controller:'postOffice']"
										  update="[success:'itemList', failure:'message']"
										  value="${message(code:'post.send.item')}"
										  onclick="\$('#postError').empty();"/>
					</g:form>

				</div>
			</g:if>
			<g:else>
				<g:message code="post.no_items"/>
			</g:else>

		<!-- end of items list -->

		</div>
	</div>

	<div class="col3">
		<g:render template="/shared/player_character" model="[showEquipment:true]"/>
	</div>
</div>

</body>
</html>