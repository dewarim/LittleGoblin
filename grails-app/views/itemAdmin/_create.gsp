<h2><g:message code="itemType.create"/></h2>
<g:render template="/shared/hideShow" model="[elementId:'createForm']"/>
<div class="create_form" id="createForm" style="display:none;">
	<g:form>
		<input type="hidden" name="indirectSubmit" value="create">

		<g:render template="fields" model="[itemType:itemType]"/>
		<g:submitToRemote
				url="[controller:'itemAdmin', action:'save']"
				update="[success:'itemTypeList', failure:'message']"
				value="${message(code:'save')}"
				onSuccess="\$('#message').text('${message(code:'create.success')}');"/>
	</g:form>
</div>