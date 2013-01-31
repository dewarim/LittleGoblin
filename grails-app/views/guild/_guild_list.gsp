<%@ page import="de.dewarim.goblin.guild.Guild" %>
<div id="guild_list">
    <div id="guild_description">&nbsp;</div>
    <table class="guild_list_table" bguild="1">
        <thead>
        <tr>
            <th><g:message code="guild.name"/></th>
            <th><g:message code="guild.description.th"/></th>
            <th><g:message code="guild.incomeTax"/></th>
            <th><g:message code="guild.entryFee"/></th>
            <th><g:message code="guild.membership"/></th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${guilds.sort { it.name }}" var="guild">
            <g:set var="isMember" value="${guildMemberService.checkMembership(pc, guild)}"/>
            <tr>
                <td>
                    <g:if test="${isMember}">
                        <g:link action="show" controller="guild" params="[guild: guild.id]">
                            <g:message code="${guild.name}"/>
                        </g:link>
                    </g:if>
                    <g:else>
                        <g:message code="${guild.name}"/>
                    </g:else>
                </td>
                <td>
                    <g:remoteLink action="describe" controller="guild"
                                  update="[success: 'guild_description', failure: 'message']"
                                  params="[guild: guild.id]">
                        <g:message code="guild.info"/>
                    </g:remoteLink>
                </td>
                <td>
                    ${guild.incomeTax}
                </td>
                <td>
                    ${guild.entryFee}
                </td>
                <td>
                    <g:if test="${isMember}">
                        <g:link action="leave" controller="guild" params="[guild: guild.id]">
                            <g:message code="guild.leave"/>
                        </g:link>

                    </g:if>
                    <g:else>
                        <g:link action="join" controller="guild" params="[guild: guild.id]">
                            <g:message code="guild.join"/>
                        </g:link>
                    </g:else>
                </td>
            </tr>
        </g:each>

        </tbody>
    </table>
    <g:set var="guildCount" value="${Guild.count()}"/>
    <g:if test="${guildCount}">
        <g:if test="${offset > 0}">
            <!-- previous -->
            <g:set var="previousOffset" value="${offset - max}"/>
            <g:remoteLink action="list" controller="guild" params="[max: max, offset: previousOffset]"
                          update="[success: 'guild_list', failure: 'message']">
                <g:message code="list.previous"/>
            </g:remoteLink>
        </g:if>
        &nbsp;&nbsp;
        <g:if test="${offset + max < guildCount}">
            <!-- next -->
            <g:set var="nextOffset" value="${offset + max}"/>
            <g:remoteLink action="list" controller="guild" params="[max: max, offset: nextOffset]"
                          update="[success: 'guild_list', failure: 'message']">
                <g:message code="list.next"/>
            </g:remoteLink>
        </g:if>

    </g:if>
</div>