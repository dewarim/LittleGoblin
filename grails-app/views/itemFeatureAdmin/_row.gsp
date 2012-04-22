	<td>
		${itemFeature.feature.name}
	</td>
	<td>
		${itemFeature.itemType.name}
	</td>
	<td>
      <pre>
        ${itemFeature.config}
      </pre>
	</td>
	<td>
		<g:remoteLink
				controller="itemFeatureAdmin" action="edit"
				update="[success:'edit_'+itemFeature.id, failure:'message']"
				onSuccess="\$('#message').text('');"
				params="[id:itemFeature.id]">
			[<g:message code="edit"/>]
		</g:remoteLink>
		&nbsp;
		<g:remoteLink
				controller="itemFeatureAdmin" action="delete"
				update="[success:'message', failure:'message']"
				params="[id:itemFeature.id]"
				onSuccess="\$('#edit_${itemFeature.id}').hide();">
			[<g:message code="delete"/>]
		</g:remoteLink>
	</td>
