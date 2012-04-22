<%@ page import="de.dewarim.goblin.town.Academy; de.dewarim.goblin.guild.Guild; de.dewarim.goblin.town.Town" %>
<h2><g:message code="guild.create"/></h2>
<g:render template="/shared/hideShow" model="[elementId:'createForm']"/>
<div class="create_form" id="createForm" style="display:none;">
	<g:form>
		<input type="hidden" name="indirectSubmit" value="create">

		<g:render template="fields" model="[guild:guild]"/>
		<g:submitToRemote
				url="[controller:'guildAdmin', action:'save']"
				update="[success:'guildList', failure:'message']"
				value="${message(code:'save')}"
				onSuccess="\$('#message').text('${message(code:'create.success')}');"/>
	</g:form>
</div>