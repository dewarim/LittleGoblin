<label class="post_office" for="item_to_send"><g:message code="post.item.label"/> </label>
<g:select name="item" id="item_to_send" from="${items}" optionKey="id" optionValue="${{message(code:it.type.name) + '('+it.amount+')'}}"></g:select>

<g:if test="${sendSuccess}">
	<script type="text/javascript">
		$('#message').text('<g:message code="post.success"/>');
		$('#inventory').load('<g:resource dir="postOffice" file='loadInventory'/>');
	</script>
</g:if>
<g:if test="${items?.size() == 0}">
	<script type="text/javascript">
		$('#sendItem').text('<g:message code="post.no_items"/>');
	</script>
</g:if>