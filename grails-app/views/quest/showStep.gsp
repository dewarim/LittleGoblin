<!DOCTYPE HTML>
<html>
<head>

	<meta name="layout" content="main"/>
    <g:render template="/shared/header"/>	


</head>
<body class=" main quest ">
<g:render template="/shared/logo"/>
    <div class="navigation">

	</div>

<div class="colmask ">
	<div class="col1">


	</div>
	<div class="col2">
		<h1><g:message code="${step.title}"/></h1>
		<g:if test="${flash.message}">
			<div class="message">${flash.message}</div>
		</g:if>
		<div class=" description quest_text ">
			<g:message code="${step.description}"/>
		</div>

		<g:if test="${step.endOfQuest}">
			<g:link action="finishQuest" controller="quest" params="[pc:pc.id]">
				<g:message code="quest.finish"/>
			</g:link>
		</g:if>
		<g:else>
			<div class=" decision ">

			<g:if test="${step.automaticTransition}">
				<g:link action="showStep" controller="quest" params="[pc:pc.id]">
					<g:message code="quest.step.continue"/>
				</g:link>
			</g:if>
			<g:else>
				<ul>
					<g:each in="${step.nextSteps}" var="nextStep">
						<li>
							<g:link action="nextStep" controller="quest" params="[pc:pc.id, step:nextStep.child.id]">
								<g:message code="${nextStep.child.intro ? nextStep.child.intro : nextStep.child.title}"/>
							</g:link>
						</li>
					</g:each>

				</ul>
			</g:else>
			</div>
		</g:else>
	</div>
	<div class="col3">
		<g:render template="/shared/player_character" model="[showEquipment:true]"/>
	</div>
</div>
<g:render template="/shared/footer"/>
</body>
</html>