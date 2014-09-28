<!DOCTYPE HTML>
<html>
<head>

    <meta name="layout" content="main_with_inventory"/>

</head>

<body class=" main ">

<content tag="nav">
    <ul>
        <li>
            <g:link controller="town" action="show" params="[pc: pc.id]"><g:message code="link.to.town"/></g:link>
        </li>
        <li>
            <g:link controller="production" action="workshop"><g:message code="link.to.workshop"/></g:link>
        </li>
    </ul>
</content>

<div class="pc_main">

    <h1><g:message code="workshop.h"/></h1>

    <div id="message" class="message">
        <g:if test="${flash.message}">${flash.message}</g:if>
    </div>

    <h2><g:message code="workshop.production.jobs"/></h2>
    <table class="job_table" cellpadding="8">
        <thead>
        <tr>
            <th><g:message code="product.name"/></th>
            <th><g:message code="job.finished.th"/></th>
            <th><g:message code="job.cancel"/></th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${jobs}" var="job">
            <tr>
                <td><g:message code="${job.product.name}"/></td>
                <td>
                    <g:message code="job.finished"
                               args="${[g.formatDate(date: job.finished, type: 'datetime', style: 'MEDIUM')]}"/>
                </td>
                <td>
                    <g:link action="cancelProductionJob" controller="production" params="[job: job.id]">
                        <g:message code="job.cancel.link"/>
                    </g:link>
                </td>
            </tr>
        </g:each>

        </tbody>
    </table>
</div>

</body>
</html>