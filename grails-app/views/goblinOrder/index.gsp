<!DOCTYPE HTML>
<%@ page import="de.dewarim.goblin.GlobalConfigService" %>
<html>
<head>
    <meta name="layout" content="main_with_inventory"/>

</head>

<body class=" main ">

<content tag="nav">
    <g:link controller="town" action="show" params="[pc: pc.id]"><g:message code="link.to.town"/></g:link>
</content>

<div class="pc_main">

    <h1 class="order_hall"><g:message code="order.hall"/></h1>

    <div id="order-description" class="order_description">
        <g:message code="order.hall.description"/>
    </div>

    <div id="message" class="message">
        <g:if test="${flash.message}">${flash.message}</g:if>
    </div>

    <div id="order_list_all">
        <g:render template="/goblinOrder/order_list"/>
    </div>

    <div class="order_create">
        <h2 class="order_create"><g:message code="order.create.h"/></h2>

        <div class="intro">
            <g:message code="order.create.intro" args="[globalConfigService.fetchValue('coins.price.create_order')]"/>
        </div>
        <g:if test="${saveFailed}">
            <g:message code="order.save.failed"/>
            <g:renderErrors bean="${order}" as="list"/>
        </g:if>

        <g:form action="save" controller="goblinOrder" method="POST">
            <input type="hidden" name="pc" value="${pc.id}">
            <table>
                <tr>
                    <td><g:message code="order.description.th"/></td>
                    <td><g:textArea name="order_description" value="" rows="6" cols="60"/></td>
                </tr>
                <tr>
                    <td><g:message code="order.name"/></td>
                    <td><div id="check_name_result"></div>

                        <div id="order_name_field">
                            <g:remoteField action='checkOrderName' controller='goblinOrder'
                                           update="[success: 'check_name_result', failure: 'check_name_result']"
                                           name="order_name" paramName="order_name" value="${order?.name}"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>
                        <g:submitButton name="createSubmit" value="${message(code: 'order.save')}"/></td>
                </tr>
            </table>
        </g:form>
    </div>

</div>

</body>
</html>