<footer class="footer">
    <strong><a href="http://dewarim.com/browsergame/status"><g:message code="version.info"/></a></strong>
    <br/><br/>

    <div class="imprint">
        <g:link style="font-size:small" action="imprint" controller="portal">
            <g:message code="imprint.link_to"/></g:link>

        <sec:ifLoggedIn>
            &nbsp;|&nbsp; <g:link style="font-size:small" action="index" controller="logout"><g:message
                code="logout.link"/></g:link>
        </sec:ifLoggedIn>
    </div>
</footer>