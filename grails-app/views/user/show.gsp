<head>
    <meta name="layout" content="main"/>
    <title>Show User</title>
</head>

<body>

<div class="navigation">
    <span class="menuButton"><a class="home" href="${createLinkTo(dir: '')}">Home</a></span>
    <span class="menuButton"><g:link class="list" action="list">User List</g:link></span>
    <span class="menuButton"><g:link class="create" action="create">New User</g:link></span>
</div>

<div class="colmask">
    <div class="full_col2">

        <h1>Show User</h1>
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>
        <div class="dialog">
            <table>
                <tbody>

                <tr class="prop">
                    <td valign="top" class="name">ID:</td>
                    <td valign="top" class="value">${user.id}</td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">Login Name:</td>
                    <td valign="top" class="value">${user.username}</td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">Full Name:</td>
                    <td valign="top" class="value">${user.userRealName}</td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">Enabled:</td>
                    <td valign="top" class="value">${user.enabled}</td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">Description:</td>
                    <td valign="top" class="value">${user.description}</td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">Email:</td>
                    <td valign="top" class="value">${user.email}</td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">Show Email:</td>
                    <td valign="top" class="value">${user.emailShow}</td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">Roles:</td>
                    <td valign="top" class="value">
                        <ul>
                            <g:each in="${roleNames}" var='name'>
                                <li>${name}</li>
                            </g:each>
                        </ul>
                    </td>
                </tr>

                </tbody>
            </table>
        </div>

        <div class="buttons">
            <g:form>
                <input type="hidden" name="id" value="${user.id}"/>
                <span class="button"><g:actionSubmit class="edit" value="Edit"/></span>
                <span class="button"><g:actionSubmit class="delete"
                                                     onclick="return confirm('${message(code: 'user.disable.confirm')}');"
                                                     value="Delete"/></span>
            </g:form>
        </div>
    </div>

</div>
</body>
