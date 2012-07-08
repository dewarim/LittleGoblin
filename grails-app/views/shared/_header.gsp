<link rel="stylesheet" type="text/css" href="${resource(dir:'css', file:'layout.css')}">
<link rel="stylesheet" type="text/css" href="${resource(dir:'css', file:'goblin.css')}">

<script type="text/javascript" src="${resource(dir: 'js', file: 'goblin.js')}"></script>
<script type="text/javascript">

	function hideDiv(id) {
		$("#"+id).css('display','none');
	}

	function showDiv(id) {
		$("#"+id).css('display','block');
	}

	<g:if test="${pc}">
	/* This looks for updates to player_messages every 5 seconds. */
	function messageReload(){
	   $('#player_messages').load('<g:resource dir="playerCharacter" file="fetchMessages/?pc=${pc.id}"/>');
	}
	</g:if>
	
</script>
