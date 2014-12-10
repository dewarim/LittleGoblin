<td>
	${mob.name}
</td>
<td>
	${mob.description}
	<hr>
	<g:message code="${mob.description}"/>
</td>
<td>
	${mob.xpValue}
</td>
<td>
	${mob.strike?.toString()}
</td>
<td>
	${mob.parry?.toString()}
</td>
<td>
	${mob.damage?.toString()}
</td>
<td>
	${mob.initiative?.toString()}
</td>
<td>
	${mob.life.points}
</td>
<td>
	${mob.maxHp}
</td>
<td>
	${mob.gold}
</td>

<td>
	<g:message code="${mob.male ? 'default.boolean.true' : 'default.boolean.false'}"/>
</td>

<td class="images_list">
	<ul>
		<g:each in="${mob.mobImages}" var="mobImage">
			<li><g:message code="${mobImage.image.name}"/></li>
		</g:each>
	</ul>
</td>
<td>
	<g:remoteLink
			controller="mobAdmin" action="edit"
			update="[success:'edit_'+mob.id, failure:'message']"
			onSuccess="\$('#message').text('');"
			params="[id:mob.id]">
		[<g:message code="edit"/>]
	</g:remoteLink>
	&nbsp;
	<g:remoteLink
			controller="mobAdmin" action="delete"
			update="[success:'message', failure:'message']"
			params="[id:mob.id]"
			onSuccess="\$('#edit_${mob.id}').hide();">
		[<g:message code="delete"/>]
	</g:remoteLink>
</td>
