<!DOCTYPE HTML>
<html>
<head>

    <meta name="layout" content="main"/>
    <asset:stylesheet src="login.css"/>

    <title><g:message code="portal.landing.title"/></title>
    <g:if test="${grailsApplication.config.facebook.enabled}">
        <script type="text/javascript">(function (d, s, id) {
            var js, fjs = d.getElementsByTagName(s)[0];
            if (d.getElementById(id)) {
                return;
            }
            js = d.createElement(s);
            js.id = id;
            js.src = "//connect.facebook.net/en_US/all.js#appId=${grailsApplication.config.facebook?.appId}&xfbml=1";
            fjs.parentNode.insertBefore(js, fjs);
        }(document, 'script', 'facebook-jssdk'));
        </script>
    </g:if>

</head>

<body class="landing_body main">

<div class="navigation">
    <g:if test="${grailsApplication.config.facebook?.enableLikeButton}">
        <div id="fblike-root"></div>

        <div class="fb-like" data-href="${grailsApplication.config.facebook?.myUrl}" data-send="false" data-width="450"
             data-show-faces="false" data-font="tahoma"></div>
    </g:if>
</div>


<div class="colmask ">

    <div class="landing_col2">
        <h1 class="landing_title"><g:message code="portal.landing.title"/></h1>
        <g:if test="${flash.message}">
            <div class="message" style="margin-left:25%;margin-right:25%;margin-bottom: 2em;">${flash.message}</div>
        </g:if>
        <img class="goblin" style="float:left; padding:2ex;" src="http://images.schedim.de/LittleGoblinSmall.png"
             border="0" alt="Image of a friendly little goblin by Richard Morris (YAFGC)">

        <noscript>
            <div class="font-size:large;font-color:red;"><g:message code="please_use_javascript"/></div>
        </noscript>

        <div class="landing_login">
            <sec:ifLoggedIn>
                <p>
                    <g:message code="logged.in.as"/>:
                    <sec:loggedInUserInfo field="username"/>
                </p>
                <sec:ifAnyGranted roles="ROLE_ADMIN">
                    <g:link action="index" controller="admin">
                        <g:message code="link.to.admin"/>
                    </g:link>
                    <br>
                </sec:ifAnyGranted>
                <sec:ifNotGranted roles="ROLE_ADMIN">
                    <g:link action="start" controller="portal">
                        <g:message code="link.to.characters"/>
                    </g:link>
                </sec:ifNotGranted>

            </sec:ifLoggedIn>
            <sec:ifNotLoggedIn>
                <g:render template="/shared/login" model="[postUrl: postUrl]"/>
                <!-- <g:link action="start" controller="portal"><g:message code="link.to.login"/></g:link>
                <br>
                <br>
                <g:if test="${grailsApplication.config.facebook?.enableFacebookLogin}">
                    <div class="fb-login-button" data-show-faces="false" data-width="200" data-max-rows="1"></div>
                </g:if>
                -->

            </sec:ifNotLoggedIn>

        </div>

        <div class="clear">
            <p>This is a demo installation of Little Goblin, a fantasy game based upon the framework by the same name,
            which is currently under development. This test server will be reset with each update.
            </p>

            <p>You are invited to test the current functionality by using the "anon" login with password "anon".

            If you want to report any bugs or if you miss a specific feature, please file a
                <a href="https://github.com/dewarim/LittleGoblin/issues/">bug report</a> or
                or a
                <a href="https://github.com/dewarim/LittleGoblin/issues/">feature request</a> on GitHub.com
            </p>

            <p>You can also just send a mail ( ingo_wiarda@dewarim.de ) or tweet me: <a
                    href="http://twitter.com/#!dewarim">twitter.com/#!/dewarim</a></p>

            <p>Currently, the recommended browser is Firefox - this site looks fugly in IE, but should be usable.</p>
        </div>

        <div class="documentation_info">
            <p>Documentation for Little Goblin can be found at <a href="http://littlegoblin.de">LittleGoblin.de</a>.</p>
        </div>

        <div class="version_info">
            <pre>
                Version 0.5.2.x ""
                * Changed link to issue tracker (use GitHub instead of Sourceforge).
                * Refactored FightService slightly to improve code reusability in project Schedim.
                * Link to complete changelog is working again.
            </pre>
            <pre>
                Version 0.5.0 "Small Updates" / 2015-07-11
                * Updated plugin versions

                Version 0.5.1 "Fix Panic" / 2015-07-11
                * Fixed broken many-to-many relations ("I haven't changed a thing!")
            </pre>
            <pre>
                Version 0.4.9 "Infernal Servant Error" / 2015-01-03
                * Fix typos
                * Translate mote messages to German
                * Improve tests
                * On start page, show link to admin page for admins
                * Refactor Grand Melee and fight code
            </pre>
            <div class="">
                <a href="${assetPath(src: 'status.txt')}" target="_blank"><g:message code="link.to.status.old"/></a>
            </div>

            <div class="highscore_link">
                <g:link action="show" controller="score"><g:message code="link.to.score"/></g:link>
            </div>


            <div class="langSelect">
                <br>
                <g:form name="langSelectForm" controller="portal" action="landing">
                    <label for="langSelectField"><g:message code="language.select"/></label>
                    <select id="langSelectField" name="lang" onchange="document.langSelectForm.submit();">
                        <option value="en"><g:message code="language.english"/></option>
                        <option value="fr"><g:message code="language.french"/></option>
                        <!-- <option value="de"></option> -->
                    </select>
                    <noscript><g:submitButton name="changeLang" value="${message(code: 'language.change')}"/></noscript>
                </g:form>
            </div>

        </div>

    </div>

    <div class="col3">
        <div class="landing_about">
            <h2><g:message code="landing.about.title"/></h2>
            <g:message code="landing.about.text"/>
        </div>

    </div>
</div>

<g:if test="${grailsApplication.config.facebook?.enabled}">
    <div id="fb-root"></div>
</g:if>
</body>
</html>