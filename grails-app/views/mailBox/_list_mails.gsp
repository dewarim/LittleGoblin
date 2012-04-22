	<table class="mailbox_messages">
		<thead>
			<tr>
				<th><g:message code="mail.delete"/> </th>
				<th><g:message code="mail.from"/> </th>
				<th><g:message code="mail.subject"/> </th>
				<th><g:message code="mail.date"/> </th>
				<g:if test="${! mailBox.typeIsArchive()}">
					<th><g:message code="mail.move.to.archive"/> </th>
				</g:if> 
			</tr>
		</thead>
		<tbody>
			<g:each in="${mails}" var="m">
				<tr id="mail_row_${m.id}">
					<td><g:remoteLink action="deleteMail"
								controller="mailBox"
								update="[success:'message', failure:'message']"
								onSuccess="hideRow('mail_row_${m.id}');"
								params="[mail:m.id]"
						>
							<g:message code="mail.delete"/> </td>	
						</g:remoteLink>
					<td>${m.sender.name}</td>
					<td><g:remoteLink action="showMail"
							controller="mailBox"
				        	update="[success:'mail_content', failure:'message']"
							params="[mail:m.id]"
					>
						<span id="mail_${m.id}">${m.subject ?: message(code:'mail.no_subject')}</span>
						</g:remoteLink>
					</td>
					<td><g:formatDate type="datetime" style="MEDIUM" date="${m.sent}"/></td>
					<g:if test="${! mailBox.typeIsArchive()}">
						<td><g:remoteLink action="archiveMail"
								controller="mailBox"
								update="[success:'message', failure:'message']"
								onSuccess="hideRow('mail_row_${m.id}');"
								params="[mail:m.id]"
						>
							<g:message code="mail.move.to.archive"/> </td>
						</g:remoteLink>
					</g:if>
				</tr>
			</g:each>
		</tbody>
	</table>
	<g:if test="${mails?.size() > 0}">
		<g:if test="${offset > 0}">
			<!-- previous -->
			<g:set var="previousOffset" value="${ offset - max}"/>
			<g:remoteLink action="listMails"
					controller="mailBox"
					params="[max:max, offset:previousOffset, box:mailBox.id]"
					update="[success:'message_list', failure:'message']">
				⋘ <g:message code="list.previous"/>
			</g:remoteLink>
		</g:if>
		&nbsp;&nbsp;
		<g:if test="${offset + max < mails.size() }">
			<!-- next -->
			<g:set var="nextOffset" value="${ offset+max }"/>
			<g:remoteLink action="listMails"
					controller="mailBox"
					params="[max:max, offset:nextOffset, box:mailBox.id]"
					update="[success:'message_list', failure:'message']">
				<g:message code="list.next"/> ⋙
			</g:remoteLink>
		</g:if>

	</g:if>