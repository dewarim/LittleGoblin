<g:if test="${registrationSuccess}">
<%-- Registration was succcessful, do not display registration form. Show confirmation message instead. --%>
    <p>
        <g:message code="registration.mail.sent" args="[username, email]"/>
    </p>
</g:if>
<g:else>

    <g:form controller="portal" action="doRegister">
        <g:if test="${problems?.size() > 0}">
            <ul>
                <g:each in="${problems}" var="problemDescription">
                    <li>${problemDescription}</li>
                </g:each>
            </ul>
        </g:if>

        <table class="registration_form">
            <tbody>
            <tr>
                <td><label for="r_username">
                    <g:message code="registration.username"/>
                </label>
                </td>
            </tr><tr>
                <td><input type="text" id="r_username" name="username"
                           value="${username}"
                           title="${message(code: 'registration.minNameLength', args: [minNameLength])}"></td>
            </tr>
            <tr>
                <td><label for="r_email">
                    <g:message code="registration.email"/>
                </label></td>
            </tr><tr>
                <td><input type="text" id="r_email" name="email" value="${email}"
                           title="${message(code: 'registration.need.email')}"></td>
            </tr>
            <tr>
                <td><label for="r_password">
                    <g:message code="registration.password"/>
                </label>
                </td>
            </tr><tr>
                <td><input type="password" id="r_password" name="password"
                           title="${message(code: 'registration.minPassLength', args: [minPassLength])}"></td>
            </tr>
            <tr>
                <td><label for="r_password2"><g:message code="registration.password2"/></label>
                </td>
            </tr><tr>
                <td><input type="password" id="r_password2" name="password2"
                           title="${message(code: 'registration.repeat.pass')}"></td>
            </tr>
            <tr>
                <td class="right">
                    <g:submitToRemote url="[action: 'doRegister', controller: 'portal']"
                                      update="[success: 'registrationForm', failure: 'registrationForm']"
                                      value="${message(code: 'registration.submit')}"/>
                </td>
            </tr>
            </tbody>
        </table>
    </g:form>
</g:else>