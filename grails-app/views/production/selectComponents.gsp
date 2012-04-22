<!DOCTYPE HTML>
<html>
<head>

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
			<h2><g:message code="workshop.select.components"/> </h2>
			<table class="product_table">
				<thead>
				    <tr>
						<th><g:message code="product.name"/> </th>
						<th><g:message code="product.tools"/></th>
						<th><g:message code="product.input"/> </th>
						<th><g:message code="product.output"/> </th>
						<th><g:message code="product.time"/> </th>
						<th><g:message code="product.max.items"/> </th>
				    </tr>
				</thead>
				<tbody>
					<tr>
						<td><g:message code="${product.name}"/> </td>
						<td>
							<ul>
								<g:each in="${product.fetchTools()}" var="tool">
									<li><g:message code="${tool.name}"/> </li>
								</g:each>
							</ul>
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
							${maxItems}
						</td>
					</tr>

				</tbody>
			</table>

			<div id="selectItems">
				<g:render template="/production/selectItems" model="[product:product, pc:pc, itemMap:itemMap, selectedItems:[:]]"/>
			</div>

		</div>
	</div>
	<div class="col3">
		<g:render template="/shared/player_character" model="[showEquipment:true]"/>
	</div>
</div>
<g:render template="/shared/footer"/>
</body>
</html>