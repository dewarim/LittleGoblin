<!DOCTYPE HTML>
<html>
<head>

	<meta name="layout" content="main"/>
   	

	
</head>
<body class=" main ">
<g:render template="/shared/logo"/>
    <div class="navigation">
        <g:link controller="town" action="show" params="[pc:pc.id]"><g:message code="link.to.town"/></g:link>
    </div>
<div class="colmask ">
	<div class="col1">

		<div id="inventory" class="inventory">
			<g:render template="/shared/sideInventory"/>
		</div>

	</div>
	<div class="col2">
		<div class="pc_main">

			<h1><g:message code="workshop.h"/></h1>
			<div id="message" class="message">
				<g:if test="${flash.message}">${flash.message}</g:if>
			</div>


			<h2><g:message code="workshop.jobs.list"/> </h2>
			<g:if test="${pc.productionJobs.isEmpty()}" >
				<g:message code="production.list.empty"/>
			</g:if>
			<g:else>
				<g:message code="production.job.count" args="[pc.productionJobs.size()]"/>
				<g:link action="listProductionJobs" controller="production">
					<g:message code="link.to.productionJobs"/>
				</g:link>
			</g:else>

			<h2><g:message code="workshop.categories"/> </h2>
			<ul>
			<g:each in="${categories}" var="category">
				<li><g:link action="listProducts" controller="production" params="[category:category.id]"><g:message code="${category.name}"/> </g:link></li>
			</g:each>
			</ul>
		</div>
	</div>
	<div class="col3">
		<g:render template="/shared/player_character" model="[showEquipment:true]"/>
	</div>
</div>
<g:render template="/shared/footer"/>
</body>
</html>