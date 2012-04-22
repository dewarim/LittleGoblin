<g:form method="POST">
	<input type="hidden" name="pc" value="${pc.id}"/>
	<g:textArea name="description" value="${pc.description}" rows="6" columns="80"/>
	<g:submitToRemote url="[action:'saveDescription', controller:'playerCharacter']" update="[success:'pc_description', failure:'message']" value="${message(code:'pc.save_description')}"/>
</g:form>