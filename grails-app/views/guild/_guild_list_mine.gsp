<div id="guild_list">
	<div id="guild_description">&nbsp;</div>
	<table class="guild_list_table" bguild="1">
		<tr>
			<th><g:message code="guild.name"/></th>
			<th><g:message code="guild.incomeTax"/> </th>
			<th><g:message code="guild.membership"/> </th>
		</tr>
		<g:each in="${guilds.sort{it.name}}" var="guild">
			<tr>
				<td>
					<g:link controller="guild" action="show" params="[guild:guild.id]">
						<g:message code="${guild.name}"/>
					</g:link>
				</td>			
				<td class="decimal">
					${guild.incomeTax}
				</td>
				<td>
					<g:link action="leave" controller="guild" params="[guild:guild.id]">
						<g:message code="guild.leave"/>
					</g:link>
				</td>
			</tr>
		</g:each>
	</table>
	<g:set var="guildCount" value="${guilds.size()}"/>
	<g:if test="${guildCount}">
		<g:if test="${offset > 0}">
			<!-- previous -->
			<g:set var="previousOffset" value="${ offset - max}"/>
			<g:remoteLink action="listMine" controller="guild" params="[max:max, offset:previousOffset]" update="[success:'guild_list', failure:'message']">
				<g:message code="list.previous"/>
			</g:remoteLink>
		</g:if>
		&nbsp;&nbsp;
		<g:if test="${offset + max < guildCount }">
			<!-- next -->
			<g:set var="nextOffset" value="${ offset+max }"/>
			<g:remoteLink action="listMine" controller="guild" params="[max:max, offset:nextOffset]" update="[success:'guild_list', failure:'message']">
				<g:message code="list.next"/>
			</g:remoteLink>
		</g:if>

	</g:if>
</div>