<g:if test="${mobs?.size() > 0}">

<table class="adminTable">
	<thead>
	<tr>
		<th><g:message code="name"/></th>
		<th><g:message code="description"/></th>
		<th><g:message code="mob.xpValue"/></th>
		<th><g:message code="mob.strike"/></th>
		<th><g:message code="mob.parry"/></th>
		<th><g:message code="mob.damage"/></th>
		<th><g:message code="mob.initiative"/></th>
		<th><g:message code="mob.hp"/></th>
		<th><g:message code="mob.maxHp"/></th>
		<th><g:message code="mob.gold"/></th>
		<th><g:message code="mob.male"/></th>
		<th><g:message code="mob.images"/></th>
		<th><g:message code="options"/></th>
	</tr>
	</thead>
	<tbody>
	<g:each in="${mobs}" var="mob">
		<tr id="edit_${mob.id}" class="editable_table">
			<g:render template="update" model="[mob:mob]"/>
		</tr>
	</g:each>
	</tbody>
</table>
	</g:if>
<g:else>
	<g:message code="admin.none.defined"/>
</g:else>