<!DOCTYPE HTML>
<html>
<head>

	<meta name="layout" content="main"/>
   	

</head>
<body class="main">
<g:render template="/shared/logo"/>
    <div class="navigation">
        <g:link action="landing" controller="portal"><g:message code="landing.link.to.home"/> </g:link>
    </div>
<div class="colmask ">
	<div class="col1">



	</div>
	<div class="full_col2">
            &nbsp;
		  	<h2><g:message code="landing.highscore"/></h2>
		<g:if test="${highscore}">
			<table>
				<tr>
					<th><g:message code="highscore.rank"/></th>
					<th><g:message code="highscore.name"/></th>
					<th><g:message code="highscore.xp"/></th>
					<th><g:message code="highscore.cause_of_death"/></th>
				</tr>
				<g:set var="counter" value="1"/>
				<g:each in="${highscore}" var="entry">
					<tr>
						<td>${counter++}</td>
						<td>${entry.character.name}</td>
						<td>${entry.xp}</td>
						<td>${entry.killer?.name}</td>
					</tr>
				</g:each>
			</table>
		</g:if>
		<g:else>
			<g:message code="highscore.none"/>
		</g:else>
	</div>
	<!--<div class="col3">

	</div>-->
 </div>

</body>
</html>