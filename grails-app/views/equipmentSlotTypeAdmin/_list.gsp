				<g:each in="${slotList}" var="equipmentSlotType">
					<li id="edit_${equipmentSlotType.id}" class="editable_list">
						${equipmentSlotType.name}
						<g:remoteLink
							controller="equipmentSlotTypeAdmin" action="edit"
							update="[success:'edit_'+equipmentSlotType.id, failure:'message']"
							params="[slotId:equipmentSlotType.id]"
						>
							[<g:message code="edit"/>]
						</g:remoteLink>
						&nbsp;
						<g:remoteLink
							controller="equipmentSlotTypeAdmin" action="delete"
							update="[success:'message', failure:'message']"
							params="[slotId:equipmentSlotType.id]"
							onSuccess="\$('#edit_${equipmentSlotType.id}').hide();"
						>
							[<g:message code="delete"/>]
						</g:remoteLink>
					</li>
				</g:each>