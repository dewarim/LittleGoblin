				<g:each in="${attributeList}" var="combatAttribute">
					<li id="edit_${combatAttribute.id}" class="editable_list">
						${combatAttribute.name}
						<g:remoteLink
							controller="combatAttributeAdmin" action="edit"
							update="[success:'edit_'+combatAttribute.id, failure:'message']"
							params="[attributeId:combatAttribute.id]"
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
					</li>
				</g:each>