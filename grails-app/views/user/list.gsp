<%@ page import="de.dewarim.goblin.UserAccount" %>
<head>
    <meta name="layout" content="main"/>
    <title>User List</title>
</head>

<body>

<div class="navigation">
    <span class="menuButton"><a class="home" href="${createLinkTo(dir: '')}">Home</a></span>
    <span class="menuButton"><g:link class="create" action="create">New User</g:link></span>
</div>

<div class="colmask">
    <div class="full_col2">

        <h1><g:message code="user.list.head"/></h1>
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>
        <div class="list">
            <table>
                <thead>
                <tr>
                    <g:sortableColumn property="id" title="Id"/>
                    <g:sortableColumn property="username" title="Login Name"/>
                    <g:sortableColumn property="userRealName" title="Full Name"/>
                    <g:sortableColumn property="enabled" title="Enabled"/>
                    <g:sortableColumn property="description" title="Description"/>
                    <th>&nbsp;</th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${users}" status="i" var="user">
                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.userRealName}</td>
                        <td>${user.enabled}</td>
                        <td>${user.description}</td>
                        <td class="actionButtons">
                            <span class="actionButton">
                                <g:link action="show" id="${user.id}">Show</g:link>
                            </span>
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>
        </div>

        <div class="paginateButtons">
            <g:paginate total="${UserAccount.count()}"/>
        </div>
    </div>
</div>
</body>
