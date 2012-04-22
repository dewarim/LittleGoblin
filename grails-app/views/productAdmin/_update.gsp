<%@ page import="de.dewarim.goblin.Image" %>
<td>
		${product.name}
	</td>
	<td class="center">
		${product.timeNeeded}
	</td>
<td>
	<g:message code="${product.category?.name}"/>
</td>
	<td class="components list">
		<ul>
		<g:each in="${product.components?.sort{it.type}}" var="component">
			<li>${component.amount} <g:message  code="${component.itemType.name}"/> (${component.type.toString()})</li>
		</g:each>
		</ul>
	</td>
	<td class="required_skills list">
		<ul>
		<g:each in="${product.requiredSkills}" var="requirement">
			<li>${requirement.level} @ <g:message code="${requirement.skill.name}"/></li>
		</g:each>
		</ul>
	</td>
	<td>
		<g:remoteLink
				controller="productAdmin" action="edit"
				update="[success:'edit_'+product.id, failure:'message']"
				onSuccess="\$('#message').text('');"
				params="[id:product.id]">
			[<g:message code="edit"/>]
		</g:remoteLink>
		&nbsp;
		<g:remoteLink
				controller="productAdmin" action="delete"
				update="[success:'message', failure:'message']"
				params="[id:product.id]"
				onSuccess="\$('#edit_${product.id}').hide();">
			[<g:message code="delete"/>]
		</g:remoteLink>
		<br>
		<g:link controller="skillRequirementAdmin" action="index"
				params="[id:product.id]">
			<g:message code="product.edit.requirements"/>
		</g:link>
		<br>
		<g:link controller="componentAdmin" action="index"
				params="[id:product.id]">
			<g:message code="product.edit.components"/>
		</g:link>
	</td>
