<!DOCTYPE HTML>
<html>
<head>

    <meta name="layout" content="main"/>

</head>

<body>

<div class="navigation">
    <g:link controller="portal" action="landing"><g:message code="link.to.home"/></g:link>
    <g:link controller="admin" action="index"><g:message code="link.to.admin"/></g:link>
</div>

<div class="main">

    <h1><g:message code="assets.management"/> </h1>
    <h2><g:message code="assets.undecided"/> </h2>
    <g:render template="undecidedAssets" 
              model="[waitingAsses:waitingAsses,assetLinkMap:assetLinkMap]"/>
</div>

</body>
</html>