<table border="1">
	<thead>
	<tr>
		<th><g:message code="skill.name"/> </th>
		<th><g:message code="skill.level"/> </th>
		<th><g:message code="skill.initiative"/> </th>
		<th><g:message code="skill.strike"/> </th>
		<th><g:message code="skill.parry"/> </th>
		<th><g:message code="skill.damage"/> </th>
	</tr>
	</thead>
	<tbody>

<g:each var="cs" in="${combatSkills}">
	<tr>
		<td><g:message code="${cs.skill.name}"/></td>
		<td>${cs.level}</td>
		<td>${cs.skill.initiative.toString()}</td>
		<td>${cs.skill.strike.toString()}</td>
		<td>${cs.skill.parry.toString()}</td>
		<td>${cs.skill.damage.toString()}</td>
	</tr>
</g:each>
	</tbody>

</table>
