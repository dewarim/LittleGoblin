<%@ page import="de.dewarim.goblin.Image" %>
<td>
		${license.name}
	</td>
	<td>
		<p>${license.description}</p>
		<hr>
		<p><g:message code="license.description.text"/>: </p>
		<p class="license_description"><g:message code="${license.description}"/> </p>
	</td>
	<td>
		${license.url}
	</td>
	<td class="where_used_list">
		<ul>
		<g:each in="${Image.findAllByLicense(license)}" var="image">
			<li>${image.name}</li>
		</g:each>
		</ul>
	</td>
	<td>
		<g:remoteLink
				controller="licenseAdmin" action="edit"
				update="[success:'edit_'+license.id, failure:'message']"
				onSuccess="\$('#message').text('');"
				params="[id:license.id]">
			[<g:message code="edit"/>]
		</g:remoteLink>
		&nbsp;
		<g:remoteLink
				controller="licenseAdmin" action="delete"
				update="[success:'message', failure:'message']"
				params="[id:license.id]"
				onSuccess="\$('#edit_${license.id}').hide();">
			[<g:message code="delete"/>]
		</g:remoteLink>
	</td>
