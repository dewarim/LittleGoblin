<%@ page import="de.dewarim.goblin.mob.MobImage; de.dewarim.goblin.Image" %>
<td class="center">
	<p>${image.name}</p>
	<g:remoteLink action="showImage" controller="imageAdmin"
		update="[success:'largeImage_'+image.id, failure:'message']"
		params="[id:image.id]">
		<img class="image" src="${image.url}" alt="${image.description}" width="64" height="64"/>
	</g:remoteLink>
</td>
<td>
	${image.artist?.name}
</td>
<td>
	<p>${image.description}</p>
	<hr>
	<p><g:message code="image.description.text"/>:</p>
	<p class="image_description"><g:message code="${image.description}"/></p>
</td>
<td>
	<p>${image.url}</p>
	<div id="largeImage_${image.id}"></div>
</td>
<td>
	${image.sourceUrl}
</td>
<td class="where_used_list">
	<ul>
		<g:each in="${MobImage.findAllByImage(image)}" var="mobImage">
			<li>${image.name}</li>
		</g:each>
	</ul>
</td>
<td>
	<g:remoteLink
			controller="imageAdmin" action="edit"
			update="[success:'edit_'+image.id, failure:'message']"
			onSuccess="\$('#message').text('');"
			params="[id:image.id]">
		[<g:message code="edit"/>]
	</g:remoteLink>
	&nbsp;
	<g:remoteLink
			controller="imageAdmin" action="delete"
			update="[success:'message', failure:'message']"
			params="[id:image.id]"
			onSuccess="\$('#edit_${image.id}').hide();">
		[<g:message code="delete"/>]
	</g:remoteLink>
</td>
