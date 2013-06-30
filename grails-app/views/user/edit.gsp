<%@ page import="de.dewarim.goblin.Role" %>
<head>
    <meta name="layout" content="main"/>
    <title>Edit User</title>
</head>

<body>

<div class="navigation">
    <span class="menuButton"><a class="home" href="${createLinkTo(dir: '')}">Home</a></span>
    <span class="menuButton"><g:link class="list" action="list">User List</g:link></span>
    <span class="menuButton"><g:link class="create" action="create">New User</g:link></span>
</div>

<div class="colmask">
    <div class="full_col2">
        <h1>Edit User</h1>
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>
        <g:hasErrors bean="${user}">
            <div class="errors">
                <g:renderErrors bean="${user}" as="list"/>
            </div>
        </g:hasErrors>

        <div class="prop">
            <span class="name">ID:</span>
            <span class="value">${user.id}</span>
        </div>

        <g:form>
            <input type="hidden" name="id" value="${user.id}"/>
            <input type="hidden" name="version" value="${user.version}"/>

            <div class="dialog">
                <table>
                    <tbody>

                    <tr class="prop">
                        <td valign="top" class="name"><label for="username">Login Name:</label></td>
                        <td valign="top" class="value ${hasErrors(bean: user, field: 'username', 'errors')}">
                            <input type="text" id="username" name="username" value="${user.username}"/>
                        </td>
                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><label for="userRealName">Full Name:</label></td>
                        <td valign="top" class="value ${hasErrors(bean: user, field: 'userRealName', 'errors')}">
                            <input type="text" id="userRealName" name="userRealName"
                                   value="${user.userRealName}"/>
                        </td>
                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><label for="passwd">Password:</label></td>
                        <td valign="top" class="value ${hasErrors(bean: user, field: 'passwd', 'errors')}">
                            <input type="password" id="passwd" name="passwd" value=""/>
                        </td>
                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><label for="enabled">Enabled:</label></td>
                        <td valign="top" class="value ${hasErrors(bean: user, field: 'enabled', 'errors')}">
                            <g:checkBox name="enabled" value="${user.enabled}"/>
                        </td>
                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><label for="description">Description:</label></td>
                        <td valign="top" class="value ${hasErrors(bean: user, field: 'description', 'errors')}">
                            <input type="text" id="description" name="description"
                                   value="${user.description}"/>
                        </td>
                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><label for="email">Email:</label></td>
                        <td valign="top" class="value ${hasErrors(bean: user, field: 'email', 'errors')}">
                            <input type="text" id="email" name="email" value="${user?.email}"/>
                        </td>
                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><label for="emailShow">Show Email:</label></td>
                        <td valign="top" class="value ${hasErrors(bean: user, field: 'emailShow', 'errors')}">
                            <g:checkBox name="emailShow" value="${user.emailShow}"/>
                        </td>
                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><label for="roles"><g:message code="userAccount.roles.label"/></label></td>
                        <td valign="top" class="value ${hasErrors(bean: user, field: 'authorities', 'errors')}">
                            <div class="fieldcontain">
                                <g:select name="roles" from="${Role.list()}" size="12" multiple="true"
                                          optionKey="id" optionValue="${{ message(code: it.name) }}"
                                          value="${user.userRoles.collect { it.role.id }}"/>
                            </div>

                        </td>
                    </tr>

                    </tbody>
                </table>
            </div>

            <div class="buttons">
                <span class="button"><g:actionSubmit class="save" value="Update"/></span>
                <span class="button">
                    <g:actionSubmit class="delete"
                                    onclick="return confirm('${message(code: 'user.disable.confirm')}');"
                                    value="Delete"/></span>
            </div>

        </g:form>
    </div>
</div>
</body>
