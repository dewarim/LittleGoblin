<%@ page import="de.dewarim.goblin.MeleeActionType" %>
<g:if test="${meleeMessage}">
    <g:message code="${meleeMessage}"/>
</g:if>
<g:if test="${currentAction}">
    <g:message code="melee.your.action"/><br>
    <g:if test="${currentAction.type.equals(MeleeActionType.FIGHT)}">
        <g:message code="melee.attack.foo" args="[currentAction.target.name]"/>
    </g:if>
    <g:elseif test="${currentAction.type.equals(MeleeActionType.USE_ITEM)}">
        <g:message code="melee.use.foo"
                   args="[message(code:currentAction.feature.name), message(code:currentAction.item.type.name)]"/>
    </g:elseif>
</g:if>
<g:else>
    <g:if test="${pc.currentMelee}">

        <g:form name="takeActionForm">
            <input type="hidden" name="pc" value="${pc.id}"/>

            <label for="adversary">
                <g:message code="melee.action.adversary"/>
            </label>
            <g:select name="adversary" id="adversary" from="${opponents}"
                      optionKey="id" optionValue="name"/>

            <g:submitToRemote url="[controller:'melee', action:'attack']"
                              update="[success:'chooseAction', failure:'chooseAction']"
                              value="${message(code:'melee.attack')}"/>
            <br>
            <g:if test="${items.size() > 0}">
                <label for="itemFeature">
                    <g:message code="melee.action.item"/>
                </label>
                <select name="itemFeature" id="itemFeature">
                    <g:each in="${items}" var="item">
                        <g:each in="${item.type.itemTypeFeatures}" var="itemTypeFeature">
                            <option value="${item.id + '__' + itemTypeFeature.id}">
                                <g:message code="${item.type.name}"/> (<g:message code="item.effect"/>: <g:message
                                        code="${itemTypeFeature.feature.name}"/>)
                            </option>
                        </g:each>
                    </g:each>
                </select>
                <label for="target">
                    <g:message code="melee.action.target"/>
                </label>
                <g:select name="target" id="target" from="${fighters.sort{it.name}}"
                          optionKey="id" optionValue="name" noSelection="${['null':pc.name]}"/>
                <g:submitToRemote url="[controller:'melee', action:'useItem']"
                                  update="[success:'chooseAction', failure:'chooseAction']"
                                  value="${message(code:'melee.use.item')}"/>
            </g:if>
            <g:else>
                <!-- player does not carry any items to activate / use -->
            </g:else>

        </g:form>
    </g:if>
    <g:else>
        // nothing to do.
    </g:else>

</g:else>

<script type="text/javascript">
    function reloadMelee() {
        $('#chooseAction').load('<g:resource dir="melee" file="updateActions/?pc=${pc.id}"/>');
        $('#meleeFighters').load('<g:resource dir="melee" file="updateFighterList/?pc=${pc.id}"/>');
    }
    var meleeReload = setTimeout("reloadMelee()", 10000);
</script>