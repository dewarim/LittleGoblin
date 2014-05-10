<div class="undecided_assets">
    <table>
        <thead>
        <tr><g:message code="assets.id"/></tr>
        <tr><g:message code="assets.class"/></tr>
        <tr><g:message code="assets.name"/></tr>
        </thead>
        <tbody>
        <g:each in="${waitingAsses}" var="ass">
            <tr>
                <td>${ass.id}</td>
                <td>${ass.assetClass}</td>
                <g:set var="linkOptions" value="${assetLinkMap.get(ass.id)}"/>
                <td><g:link controller="${linkOptions.get('controller')}"
                            action="${linkOptions.get('action')}" 
                            id="${linkOptions.get('id')}">
                    ${ass.name}
                </g:link>
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>

</div> 