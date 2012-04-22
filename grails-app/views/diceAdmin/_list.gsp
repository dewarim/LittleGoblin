<g:if test="${dices?.size() > 0}">

<table class="adminTable">
	<thead>
	<tr>
		<g:sortableColumn property="name" titleKey="dice.name" defaultOrder="desc" action="index"/>
		<g:sortableColumn property="amount" titleKey="dice.amount" action="index"/>
		<g:sortableColumn property="sides" titleKey="dice.sides" action="index"/>
		<g:sortableColumn property="bonus" titleKey="dice.bonus" action="index"/>
		<th><g:message code="dice.options"/></th>
	</tr>
	</thead>
	<tbody>
	<g:each in="${dices}" var="dice">
		<tr id="edit_${dice.id}" class="editable_table">
			<g:render template="update" model="[dice:dice]"/>
		</tr>
	</g:each>
	</tbody>
</table>
	</g:if>
<g:else>
	<g:message code="dice.admin.none.defined"/>
</g:else>