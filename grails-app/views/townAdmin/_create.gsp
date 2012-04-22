<%@ page import="de.dewarim.goblin.town.Town" %>
<h2><g:message code="town.create"/></h2>
<g:render template="/shared/hideShow" model="[elementId:'createForm']"/>
<div class="create_form" id="createForm" style="display:none;">
	<g:form>
		<input type="hidden" name="indirectSubmit" value="create">

		<g:render template="fields" model="[town:town]"/>
		<g:submitToRemote
				url="[controller:'townAdmin', action:'save']"
				update="[success:'townList', failure:'message']"
				value="${message(code:'save')}"
				onSuccess="\$('#message').text('${message(code:'create.success')}');"/>
	</g:form>
</div>