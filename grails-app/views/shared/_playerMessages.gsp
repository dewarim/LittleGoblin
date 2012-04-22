<div id="player_messages" class="player_messages">

	<g:each in="${pcMessages}" var="pcm">
		${pcm.pcMessage}<br>
	</g:each>

<script type="text/javascript">
	var messageReloadTimer = setTimeout("messageReload()", 5000);
</script>
</div>
