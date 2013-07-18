<g:if test="${players?.size() > 0}">
    <g:select id="player-list-select" name="${fieldName}" from="${players}" optionKey="id" required=""
              optionValue="${{it.name}}"  />
</g:if>