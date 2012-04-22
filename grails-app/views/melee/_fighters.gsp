<g:if test="${fighters?.size() > 0}">
<table class="adminTable">
    <thead>
    <tr>
        <th><g:message code="name"/></th>
        <th><g:message code="pc.order"/></th>
        <th><g:message code="pc.xp"/></th>
        <th><g:message code="pc.hp"/></th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${fighters}" var="fighter">
        <tr>
            <td>${fighter.name}</td>
            <td>${fighter.goblinOrder?.name}</td>
            <td>${fighter.xp}</td>
            <td>${fighter.hp}</td>
        </tr>
    </g:each>
    </tbody>
</table>
</g:if>
<g:else>
    <p>
        <g:message code="melee.no.fighters"/>
    </p>
</g:else>