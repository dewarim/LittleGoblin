<%@ page import="de.dewarim.goblin.guild.Guild; de.dewarim.goblin.town.Town" %>
<h2><g:message code="academy.create"/></h2>
<g:render template="/shared/hideShow" model="[elementId:'createForm']"/>
<div class="create_form" id="createForm" style="display:none;">
<g:form>
	<input type="hidden" name="indirectSubmit" value="create">
	<g:render template="fields" model="[academy:academy]"/>
	<g:submitToRemote
	 	url="[controller:'academyAdmin', action:'save']"
		update="[success:'academyList', failure:'message']"
		value="${message(code:'save')}"
		onSuccess="\$('#message').text('${message(code:'create.success')}');"
	/>
</g:form>
</div>