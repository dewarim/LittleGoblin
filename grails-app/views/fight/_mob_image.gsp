<div class="images mob_image">
<g:if test="${image}">
	<img src="${image.url}"
	 alt="${image.description}"
	 <g:if test="${image.width}">
	 	width="${image.width}"
	 </g:if>
	 <g:if test="${image.height}">
	 	height="${image.height}"
	 </g:if>
	 />
	 
	 <div class="artist_link">
	 <g:set var="mob_artist" value="${image?.artist}"/>
	 <g:if test="${mob_artist}">
	 	<g:message code="image.artist"/>:
	 	<g:if test="${mob_artist.website}">
	 	 <a target="_blank" href="${resource(base:mob_artist.website)}">${mob_artist.name}</a>
	 	</g:if>
	 	<g:else>
	 	  ${mob_artist.name}
	 	</g:else>
	</div>
	
	 </g:if>
</g:if>
<g:else>
	<div class="missing_image">
		<g:message code="help.mob.missing_image"/>
	</div>
</g:else>
</div>