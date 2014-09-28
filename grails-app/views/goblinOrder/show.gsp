<!DOCTYPE HTML>
<html>
<head>

    <meta name="layout" content="main_with_inventory"/>

</head>

<body class=" main ">

<content tag="nav">
    <g:link controller="town" action="show" params="[pc: pc.id]"><g:message code="link.to.town"/></g:link>
</content>

<div class="pc_main">

    <h1><g:message code="${order.name}"/></h1>

    <div id="message" class="message">
        <g:if test="${flash.message}">${flash.message}</g:if>
    </div>

    <div id="order_description" class="description order_description">
        ${order.description}
    </div>

    <table class="order_info_table" border="1">
        <tr>
            <td>
                <g:if test="${order.leader.male}">
                    <g:message code="order.master"/>
                </g:if>
                <g:else>
                    <g:message code="order.mistress"/>
                </g:else>
            </td>
            <td>${order.leader.name}</td>
        </tr>
        <tr>
            <td><g:message code="order.score"/></td>
            <td>${order.score}</td>
        </tr>
        <tr><td><g:message code="order.members.th"/></td>
            <td>${order.members?.size()}</td>
        </tr>
    </table>


    <h2><g:message code="order.apply.h"/></h2>
    <g:if test="${pc.goblinOrder?.equals(order) || order.applications.find { it.applicant.equals(pc) }}">
        <g:message code="order.already.applied"/>
    </g:if>
    <g:else>
        <div class="intro">
            <g:message code="order.apply.introduce_yourself"/>
        </div>

        <div id="apply_for_order">&nbsp;</div>

        <g:form action="apply" controller="goblinOrder" class="order_application_form"
                onSubmit="return confirm('${message(code: 'order.apply.confirm', args: [order.name])}');">
            <input type="hidden" name="pc" value="${pc.id}"/>
            <input type="hidden" name="order" value="${order.id}"/>
            <g:textArea name="applicationMessage" rows="8" cols="60"/><br>
            <g:submitButton name="submitApplication" value="${message(code: 'order.apply.link')}"/>
        </g:form>
    </g:else>
</div>
</body>
</html>