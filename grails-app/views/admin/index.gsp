<!DOCTYPE HTML>
<html>
<head>

	<meta name="layout" content="main"/>
   	
</head>
<body class="main">

     <div class="navigation">
			<g:link controller="portal" action="landing"><g:message code="link.to.home"/></g:link>
	 </div>
<div class="colmask ">

	<div class="full_col2">

		<h1><g:message code="admin.headline"/></h1>
		<div id="message" class="message"><g:if test="${flash.message}">${flash.message}</g:if>
		</div>
		<div class="intro">
			<g:message code="admin.intro"/>
		</div>

		<div class="admin_areas">
			<p>TODO: organize into groups.</p>
			<ul>
				<li>
					<g:link controller="combatAttributeAdmin" action="index">
						<g:message code="link.to.combatAttributeAdmin"/>
					</g:link>
				</li>
				<li>
					<g:link controller="equipmentSlotTypeAdmin" action="index">
						<g:message code="link.to.equipmentSlotTypeAdmin"/>
					</g:link>
				</li>
				<li>
					<g:link controller="academyAdmin" action="index">
						<g:message code="link.to.academyAdmin"/>
					</g:link>
				</li>
				<li>
					<g:link controller="guildAdmin" action="index">
						<g:message code="link.to.guildAdmin"/>
					</g:link>
				</li>
				<li>
					<g:link controller="townAdmin" action="index">
						<g:message code="link.to.townAdmin"/>
					</g:link>
				</li>
				<li>
					<g:link controller="artistAdmin" action="index">
						<g:message code="link.to.artistAdmin"/>
					</g:link>
				</li>
				<li>
					<g:link controller="configAdmin" action="index">
						<g:message code="link.to.configAdmin"/>
					</g:link>
				</li>
				<li>
					<g:link controller="diceAdmin" action="index">
						<g:message code="link.to.diceAdmin"/>
					</g:link>
				</li>
				<li>
					<g:link controller="shopOwnerAdmin" action="index">
						<g:message code="link.to.shopOwnerAdmin"/>
					</g:link>
				</li>
				<li>
					<g:link controller="shopAdmin" action="index">
						<g:message code="link.to.shopAdmin"/>
					</g:link>
				</li>
				<li>
					<g:link controller="categoryAdmin" action="index">
						<g:message code="link.to.categoryAdmin"/>
					</g:link>
				</li>
				<li>
					<g:link controller="licenseAdmin" action="index">
						<g:message code="link.to.licenseAdmin"/>
					</g:link>
				</li>
				<li>
					<g:link controller="imageAdmin" action="index">
						<g:message code="link.to.imageAdmin"/>
					</g:link>
				</li>
				<li>
					<g:link controller="rmmAdmin" action="index">
						<g:message code="link.to.rmmAdmin"/>
					</g:link>
				</li>
				<li>
					<g:link controller="factionAdmin" action="index">
						<g:message code="link.to.factionAdmin"/>
					</g:link>
				</li>
				<li>
					<g:link controller="questAdmin" action="index">
						<g:message code="link.to.questAdmin"/>
					</g:link>
				</li>
				<li>
					<g:link controller="encounterAdmin" action="index">
						<g:message code="link.to.encounterAdmin"/>
					</g:link>
				</li>
				<li>
					<g:link controller="questGiverAdmin" action="index">
						<g:message code="link.to.questGiverAdmin"/>
					</g:link>
				</li>
				<li>
					<g:link controller="questStepAdmin" action="index">
						<g:message code="link.to.questStepAdmin"/>
					</g:link>
				</li>
				<li>
					<g:link controller="productAdmin" action="index">
						<g:message code="link.to.productAdmin"/>
					</g:link>
				</li>
				<li>
					<g:link controller="productCategoryAdmin" action="index">
						<g:message code="link.to.productCategoryAdmin"/>
					</g:link>
				</li>
				<li>
					<g:link controller="mobAdmin" action="index">
						<g:message code="link.to.mobAdmin"/>
					</g:link>
				</li>
                <li>
					<g:link controller="itemAdmin" action="index">
						<g:message code="link.to.itemAdmin"/>
					</g:link>
				</li>
                <li>
					<g:link controller="featureAdmin" action="index">
						<g:message code="link.to.featureAdmin"/>
					</g:link>
				</li>
              <li>
					<g:link controller="itemFeatureAdmin" action="index">
						<g:message code="link.to.itemFeatureAdmin"/>
					</g:link>
				</li>
			</ul>
		</div>
	</div>

</div>

</body>
</html>