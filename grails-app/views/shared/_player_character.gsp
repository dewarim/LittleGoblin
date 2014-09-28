<div class="fight_character">
    <h2><g:message code="${pc.name}"/></h2>
    <table>
        <tr>
            <td><g:message code="pc.hp"/></td>
            <td>${pc.hp} / ${pc.maxHp}</td>
        </tr>
        <tr>
            <td><g:message code="pc.gold"/></td>
            <td id="pc_gold">${pc.gold}</td>
        </tr>
        <tr>
            <td><g:message code="pc.xp"/></td>
            <td>${pc.xp}</td>
        </tr>
        <tr>
            <td><g:message code="pc.level"/></td>
            <td>${pc.level}</td>
        </tr>
        <tr>
            <td><g:message code="pc.order"/></td>
            <td><g:if test="${pc.goblinOrder}">
                <g:link action="showMyOrder" controller="goblinOrderAdmin" params="[pc:pc.id, order:pc.goblinOrder.id]">
                   <g:message code="${pc.goblinOrder.name}"/>
                </g:link>
            </g:if>
            <g:else>-</g:else>
            </td>
        </tr>
        <tr>
            <td><g:message code="pc.coins"/></td>
            <td>${pc.user.coins}</td>
        </tr>
    </table>

    <g:if test="${showEquipment}">
        <div id="equipment" class="equipment">
            <g:render template="/shared/equipment" model="[pc:pc, items: pc.items]"/>
        </div>
    </g:if>

    <h2><g:message code="player.last_events"/></h2>
    <g:render template="/shared/playerMessages"/>

</div>