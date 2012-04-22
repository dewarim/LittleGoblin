<g:set var="hideId" value="${(int) Math.random()*100000}"/>
<div id="hide_${hideId}" style="display:none;">
	<g:link onclick="hideDiv('${elementId}');showDiv('show_${hideId}');hideDiv('hide_${hideId}');return false;" url="#">
		<g:message code="element.hide.form"/>
	</g:link>
</div>
<div id="show_${hideId}">
	<g:link onclick="showDiv('${elementId}');showDiv('hide_${hideId}');hideDiv('show_${hideId}');return false;" url="#">
		<g:message code="element.show.form"/>
	</g:link>
</div>