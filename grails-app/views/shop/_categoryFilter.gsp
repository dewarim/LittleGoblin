<div id="categoryFilter">
	<script type="text/javascript" language="javascript">
		function reloadCategories() {
		<g:remoteFunction action="reloadCategories" controller="shop" params="[shop:shop.id,pc:pc.id]"
				update="[success:'categoryFilter', failure:'message']"
			/>
		}
	</script>

	<g:remoteLink action="showAllCategories" controller="shop"
			update="[success:'itemList', failure:'message']"
			params="[shop:shop.id, pc:pc.id]"
			onSuccess="reloadCategories();">
		<g:message code="shop.filter.clear"/>
	</g:remoteLink>

	<g:each in="${categories}" var="category">
		<span id="addCategory_${category.id}" class="category_filter_disabled">
			<g:remoteLink action="addCategory" controller="shop"
					update="[success:'itemList', failure:'message']"
					params="[category:category.id, shop:shop.id, pc:pc.id]"
					onSuccess="\$('#hideCategory_${category.id}').show();\$('#addCategory_${category.id}').hide()">
				<g:message code="${category.name}"/>
			</g:remoteLink>
		</span>
		<span id="hideCategory_${category.id}" style="display:none;" class="category_filter_enabled">
			<g:remoteLink action="removeCategory" controller="shop"
					update="[success:'itemList', failure:'message']"
					params="[category:category.id, shop:shop.id,pc:pc.id]"
					onSuccess="\$('#addCategory_${category.id}').show();\$('#hideCategory_${category.id}').hide();">
				<g:message code="${category.name}"/>
			</g:remoteLink>
		</span>
	</g:each>

</div>