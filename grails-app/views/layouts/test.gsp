<!DOCTYPE html>
<html>
<head>
    <title><g:layoutTitle default="${grailsApplication.config.gameName ?: 'Little Goblin'}"/></title>
    <asset:link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon"/>
    <asset:stylesheet src="layout.css"/>
    <asset:stylesheet src="goblin.css"/>
    <asset:javascript src="application.js"/>
    <asset:stylesheet src="qunit-1.15.0.css"/>
    <asset:javascript src="qunit-1.15.0.js"/>
    <asset:javascript src="GoblinConfig.js"/>
    <asset:javascript src="Goblin.js"/>
    <asset:javascript src="goblin-test.js"/>
    <asset:javascript src="admin-test.js"/>
    <asset:deferredScripts/>
    <g:layoutHead/>
</head>

<body class="${pageProperty(name: 'body.class')}">
<g:render template="/shared/logo"/>

<div class="navigation">
    <g:pageProperty name="page.nav"/>
</div>

<g:layoutBody/>
<g:render template="/shared/footer"/>
</body>
</html>