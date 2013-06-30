${pc.description}
<g:remoteLink action="editDescription" controller="playerCharacter" update="[success:'pc_description', failure:'message']" params="[pc:pc.id]">
	<div class="edit_description"><g:message code="pc.description.edit"/></div>
</g:remoteLink>
