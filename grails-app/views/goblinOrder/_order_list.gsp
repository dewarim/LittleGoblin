<%@ page import="de.dewarim.goblin.pc.GoblinOrder" %>
<div id="order_list">
	<table class="order_list_table" border="1">
        <thead>
		<tr>
                <th><g:message code="order.name"/></th>
                <th><g:message code="order.score"/></th>
                <th><g:message code="order.members.th"/></th>
                <th><g:message code="order.description.th"/></th>
		</tr>
        </thead>
        <g:each in="${orders.sort{it.name}}" var="order">
			<tr>
				<td><g:link action="show" controller="goblinOrder" params="[order:order.id, pc:pc.id]"> <g:message code="${order.name}"/></g:link></td>
				<td>${order.score}</td>
				<td>${order.members?.size()}</td>
				<td>
					<g:remoteLink action="describe" controller="goblinOrder"
                                  update="[success:'order-description', failure:'order-description']" 
                                  params="[order:order.id, pc:pc.id]">
						<g:message code="order.info"/>
					</g:remoteLink>
				</td>
				<td>
				</td>
			</tr>
		</g:each>
	</table>
	<g:set var="goblinOrderCount" value="${GoblinOrder.count()}"/>
	<g:if test="${goblinOrderCount}">
		<g:if test="${offset > 0}">
			<!-- previous -->
			<g:set var="previousOffset" value="${ offset - max}"/>
			<g:remoteLink action="list" controller="goblinOrder" params="[max:max, offset:previousOffset]" update="[success:'order_list', failure:'message']">
				<g:message code="list.previous"/>
			</g:remoteLink>
		</g:if>
		&nbsp;&nbsp;
		<g:if test="${offset + max < goblinOrderCount }">
			<!-- next -->
			<g:set var="nextOffset" value="${ offset+max }"/>
			<g:remoteLink action="list" controller="goblinOrder" params="[max:max, offset:nextOffset]" update="[success:'order_list', failure:'message']">
				<g:message code="list.next"/>
			</g:remoteLink>
		</g:if>

	</g:if>
</div>