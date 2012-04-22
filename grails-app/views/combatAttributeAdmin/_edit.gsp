<g:form sytyle="display:inline;">
	<input type="hidden" name="attributeId" value="${combatAttribute.id}">
	<input type="hidden" name="indirectSubmit" value="update">
	<g:textField name="name" value="${combatAttribute.name}"/>
	<g:submitToRemote
	 	url="[controller:'combatAttributeAdmin', action:'update']"
		update="[success:'edit_'+combatAttribute.id, failure:'message']"
		value="${message(code:'update')}"
	/>
</g:form>