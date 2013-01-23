<td colspan="11">
    <g:form>      
        <input type="hidden" name="id" value="${itemType.id}"/>
        <h2><g:message code="itemType.basic.values"/></h2>
        <table>
            <tbody>
            <g:render template="fields" model="[itemType:itemType, itemCategoryIdList:itemCategoryIdList]"/>
            <tr>
                <td>&nbsp;</td>
                <td class="right">
                    <g:submitToRemote
                            url="[controller:'itemAdmin', action:'update']"
                            update="[success:'itemTypeList', failure:'message']"
                            value="${message(code:'update')}"
                            onSuccess="\$('#message').text('');"/>
                </td>
            </tr>

            </tbody>
        </table>

        <div class="cancel_edit">
            <p>
                <g:remoteLink
                        controller="itemAdmin" action="cancelEdit"
                        update="[success:'edit_'+itemType.id, failure:'message']"
                        params="[id:itemType.id]"
                        onSuccess="\$('#message').text('');">
                    [<g:message code="admin.cancel.edit"/>]
                </g:remoteLink>
            </p>
        </div>
    </g:form>
    <div id="requiredSlots_${itemType.id}">
        <g:render template="requiredSlots" model="[itemType:itemType]"/>
    </div>
</td>
