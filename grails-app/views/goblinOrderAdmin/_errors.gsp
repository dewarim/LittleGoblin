<g:hasErrors bean="${goblinOrder}">
    <ul class="errors">
        <g:eachError bean="${goblinOrder}">
            <li><g:message error="${it}"/></li>
        </g:eachError>
    </ul>
</g:hasErrors>