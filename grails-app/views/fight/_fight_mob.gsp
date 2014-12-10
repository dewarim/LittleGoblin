<g:render template="mob_image" model="[image:mob?.image?.image]"/>

<div class="fight_mob">
	<table>
	<tr>
		<td><g:message code="mob.name" /></td>
		<td><g:message code="${mob.name}"/></td>
	</tr>
	<tr>
		<td><g:message code="mob.hp" /></td>
		<td>${mob.life.points} / ${mob.maxHp}</td>
	</tr>
	<tr>
		<td><g:message code="mob.xpValue"/></td>
		<td>${mob.xpValue}</td>
	</tr>
	</table>
</div>