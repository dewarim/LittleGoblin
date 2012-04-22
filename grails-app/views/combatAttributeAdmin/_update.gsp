${combatAttribute.name}
					<g:remoteLink
							controller="combatAttributeAdmin" action="edit"
							update="[success:'edit_'+combatAttribute.id, failure:'message']"
							params="[attributeId:combatAttribute.id]"
						onSuccess="\$('#message').text('');"
						>
							[<g:message code="edit"/>]
						</g:remoteLink>
						&nbsp;
						<g:remoteLink
							controller="combatAttributeAdmin" action="delete"
							update="[success:'message', failure:'message']"
							params="[attributeId:combatAttribute.id]"
							onSuccess="\$('#edit_${combatAttribute.id}').hide();"
						>
							[<g:message code="delete"/>]
						</g:remoteLink>