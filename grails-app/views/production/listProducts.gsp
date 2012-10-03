<!DOCTYPE HTML>
<html>
<head>

	<meta name="layout" content="main"/>
    <g:render template="/shared/header"/>	

	
</head>
<body class=" main ">
<g:render template="/shared/logo"/>
    <div class="navigation">
        <g:link controller="town" action="show" params="[pc:pc.id]"><g:message code="link.to.town"/></g:link>
        <g:link controller="production" action="workshop"><g:message code="link.to.workshop"/> </g:link>
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
			<h2><g:message code="workshop.products" args="[message(code:category.name)]"/> </h2>


			<g:if test="${products?.isEmpty()}">
				<g:message code="product.none.available"/><br>
				<g:link controller="production" action="workshop"><g:message code="link.back.to.workshop"/> </g:link>
			</g:if>
			<g:else>

			<table class="product_table">
				<thead>
				    <tr>
						<th><g:message code="product.name"/> </th>
						<th><g:message code="product.tools"/></th>
						<th><g:message code="product.input"/> </th>
						<th><g:message code="product.output"/> </th>
						<th><g:message code="product.time"/> </th>
						<th><g:message code="product.make.th"/> </th>
				    </tr>
				</thead>
				<tbody>
				<g:each in="${products}" var="product">
					<tr>
						<td><g:message code="${product.name}"/> </td>
						<td>
							<g:set var="tools" value="${product.fetchTools()}"/>
							<g:if test="${tools.size() > 0}">
								<ul>
									<g:each in="${tools}" var="tool">
										<li><g:message code="${tool.name}"/> </li>
									</g:each>
								</ul>
							</g:if>
						</td>
						<td>
							<ul>
								<g:each in="${product.fetchInputItems()}" var="component">
									<g:set var="componentCount" value="${pc.calculateSumOfItems(component.itemType)}"/>
									<li class="input_item_${componentCount > component.amount}"> ${componentCount} / ${component.amount} <g:message code="${component.itemType.name}"/> </li>
								</g:each>
							</ul>
						</td>
						<td>
							<ul>
								<g:each in="${product.fetchOutputItems()}" var="component">
									<li>${component.amount} <g:message code="${component.itemType.name}"/> </li>
								</g:each>
							</ul>
						</td>
						<td valign="top" class="center">
							${product.timeNeeded / 1000} s
						</td>
					<td valign="top" class="center">
					<g:link action="selectComponents" controller="production" params="[product:product.id]">
						<g:message code="product.components.link"/> </g:link>

					</td>
					</tr>
				</g:each>

				</tbody>
			</table>
			</g:else>
		</div>
	</div>
	<div class="col3">
		<g:render template="/shared/player_character" model="[showEquipment:true]"/>
	</div>
</div>
<g:render template="/shared/footer"/>
</body>
</html>