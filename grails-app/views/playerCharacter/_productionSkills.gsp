<table border="1">
	<thead>
	<tr>
		<th><g:message code="skill.name"/> </th>
		<th><g:message code="skill.level"/> </th>
	</tr>
	</thead>
	<tbody>

<g:each var="cs" in="${productionSkills}">
	<tr>
		<td><g:message code="${cs.skill.name}"/></td>
		<td>${cs.level}</td>
	</tr>
</g:each>
	</tbody>

</table>
