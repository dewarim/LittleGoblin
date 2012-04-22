<h2><g:message code="fight.equipment"/></h2>
<table>
	<tr>
		<th><g:message code="equipment.slot"/></th>
		<th><g:message code="equipment.item"/></th>
	</tr>
	<g:each in="${pc.fetchEquipment()}" var="slot">
		<tr>
			<td><g:message code="${slot.name}"/></td>
			<td><g:if test="${slot.item}">

				<g:set var="resetText" value="setTextOfElement('eq_${slot.item.id}', '---')"/>
				<span id="eq_${slot.item.id}">
					<g:message code="${slot.item.type.name}"/>
						<g:remoteLink action="unequipItem" controller="item"
                                      update="[success:'inventory', failure:'message']"
                                      onComplete="${resetText}"
                                      params="[item:slot.item.id, pc:pc.id, shop:shop?.id, sideInventory:true]">
							[<g:message code="equip.remove"/>]
						</g:remoteLink>
				</span>
			</g:if>
				<g:else>
					---
				</g:else></td>
		</tr>
	</g:each>
</table>
