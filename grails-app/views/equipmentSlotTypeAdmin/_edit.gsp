<g:form sytyle="display:inline;">
	<input type="hidden" name="slotId" value="${equipmentSlotType.id}"/>
	<input type="hidden" name="indirectSubmit" value="update">
	<g:textField name="name" value="${equipmentSlotType.name}"/>
	<g:submitToRemote
	 	url="[controller:'equipmentSlotTypeAdmin', action:'update']"
		update="[success:'edit_'+equipmentSlotType.id, failure:'message']"
		value="${message(code:'update')}"
	/>
</g:form>
<g:message code="admin.ui.help" />