<div id="help_${messageId.replaceAll('.','_')}" class="help">
	<g:remoteLink controller="help" action="summonHelp"
		update="[success:'help_'+messageId.replaceAll('.','_'), failure:'message']"
		params="[messageId:messageId.encodeAsURL()]"
	>
		<g:message code="get.help"/>
	</g:remoteLink>
</div>