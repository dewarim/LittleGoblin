<!DOCTYPE HTML>
<html>
<head>

    <meta name="layout" content="main_with_inventory"/>

</head>

<body class=" main ">
<content tag="nav">
    <ul>
        <li>
            <g:link controller="town" action="show" params="[pc: pc.id]"><g:message code="link.to.town"/></g:link>
        </li>
        <li>
            <g:link controller="goblinOrder" action="showMyOrder" params="[pc: pc.id, order: order.id]">
                <g:message code="order.main.link"/>
            </g:link>
        </li>
    </ul>
</content>

<div class="pc_main">

    <h1><g:message code="${order.name}"/></h1>

    <div id="message" class="message">
        <g:if test="${flash.message}">${flash.message}</g:if>
    </div>

    <h2><g:message code="order.member.list" args="[order.name]"/></h2>

    <table class="members" border="1">
        <tr>
            <th><g:message code="order.member.name"/></th>
            <th><g:message code="order.member.level"/></th>
            <g:if test="${order.leader.equals(pc)}">
                <th><g:message code="order.member.kick.th"/></th>
            </g:if>
        </tr>
        <g:each in="${order.members.sort { it.name.toLowerCase() }}" var="member">
            <g:set var="memberId" value="member_${member.id}"/>
            <tr id="${memberId}">
                <td>${member.name}</td>
                <td>${member.level}</td>
                <g:if test="${order.leader.equals(pc)}">
                    <td>
                        <g:if test="${member.equals(pc)}">
                            ---  <!-- do not banish yourself. -->
                        </g:if>
                        <g:else>
                            <g:form>
                                <input type="hidden" name="member" value="${member.id}">
                                <g:submitToRemote update="[success: 'message', failure: 'message']"
                                                  onSuccess="hideRow('${memberId}');"
                                                  value="${message(code: 'order.member.kick.button')}"
                                                  url="[action: 'kickMember', controller: 'goblinOrder']"/>
                            </g:form>
                        </g:else>
                    </td>
                </g:if>
            </tr>
        </g:each>
    </table>
</div>
</body>
</html>