<g:form>
	<input type="hidden" name="indirectSubmit" value="create">
	<g:message code="combatAttribute.create"/><br>
	<label><g:message code="combatAttribute.name"/> </label>
	<g:textField name="name" value=""/>
	<g:submitToRemote
	 	url="[controller:'combatAttributeAdmin', action:'save']"
		update="[success:'combatAttributeTypeList', failure:'message']"
		value="${message(code:'save')}"
		onSuccess="\$('#message').text('${message(code:'create.success')}');"
	/>
</g:form>