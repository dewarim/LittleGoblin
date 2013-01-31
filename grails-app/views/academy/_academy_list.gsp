<div id="academy_list">
    <div id="academy_description" class="description">&nbsp;</div>
    <script type="text/javascript">hideDiv('academy_description');</script>
    <table class="academy_list_table">
        <thead>
        <tr>
            <th><g:message code="academy.name"/></th>
            <th><g:message code="academy.description.th"/></th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${academies.sort { it.id }}" var="academy">
            <tr>
                <td><g:link action="show" controller="academy" params="[academy: academy.id]">
                    <g:message code="${academy.name}"/>
                </g:link>
                </td>
                <td>
                    <g:remoteLink action="describe"
                                  controller="academy"
                                  update="[success: 'academy_description', failure: 'message']"
                                  params="[academy: academy.id]"
                                  onLoaded="showDiv('academy_description');">
                        <g:message code="academy.info"/>
                    </g:remoteLink>
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>
    <g:if test="${offset > 0}">
        <!-- previous -->
        <g:set var="previousOffset" value="${offset - max}"/>
        <g:remoteLink action="list" controller="academy" params="[max: max, offset: previousOffset]"
                      update="[success: 'academy_list', failure: 'message']">
            <g:message code="list.previous"/>
        </g:remoteLink>
    </g:if>
    &nbsp;&nbsp;
    <g:if test="${offset + max < academyCount}">
        <!-- next -->
        <g:set var="nextOffset" value="${offset + max}"/>
        <g:remoteLink action="list" controller="academy" params="[max: max, offset: nextOffset]"
                      update="[success: 'academy_list', failure: 'message']">
            <g:message code="list.next"/>
        </g:remoteLink>
    </g:if>
</div>