<g:form>
	<input type="hidden" name="indirectSubmit" value="create">
	<g:message code="equipmentSlotType.create"/><br>
	<label><g:message code="equipmentSlotType.name"/> </label>
	<g:textField name="name" value=""/>
	<g:submitToRemote
	 	url="[controller:'equipmentSlotTypeAdmin', action:'save']"
		update="[success:'equipmentSlotTypeTypeList', failure:'message']"
		value="${message(code:'save')}"
		onSuccess="\$('#message').text('${message(code:'create.success')}');"
	/>
</g:form>