<h2><g:message code="repMessage.create"/></h2>
<g:render template="/shared/hideShow" model="[elementId:'createForm']"/>
<div class="create_form" id="createForm" style="display:none;">
	<g:form>
		<input type="hidden" name="indirectSubmit" value="create">

		<g:render template="fields" model="[repMessage:repMessage]"/>
		<g:submitToRemote
				url="[controller:'repMessageAdmin', action:'save']"
				update="[success:'repMessageList', failure:'message']"
				value="${message(code:'save')}"
				onSuccess="\$('#message').text('${message(code:'create.success')}');"/>
	</g:form>
</div>