<!DOCTYPE HTML>
<html>
<head>

    <meta name="layout" content="main"/>
   	
    <title><g:message code="portal.landing.title"/></title>
    <g:if test="${grailsApplication.config.facebook.enabled}">
        <script type="text/javascript">(function(d, s, id) {
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
            <h2><g:message code="landing.login.title"/></h2>
            <sec:ifLoggedIn>
                <g:message code="logged.in.as"/>:
                <sec:loggedInUserInfo field="username"/>
                <br>

                <g:link action="start" controller="portal">
                    <g:message code="link.to.characters"/>
                </g:link>
            </sec:ifLoggedIn>
            <sec:ifNotLoggedIn>
                <g:message code="landing.login.text"/><br/>
                <g:link action="start" controller="portal"><g:message code="link.to.login"/></g:link>
                <br>
                <br>
                <g:link action="register" controller="portal"><g:message code="link.to.registration"/></g:link>
                <g:if test="${grailsApplication.config.facebook?.enableFacebookLogin}">
                    <div class="fb-login-button" data-show-faces="false" data-width="200" data-max-rows="1"></div>
                </g:if>
            </sec:ifNotLoggedIn>

        </div>

        <div class="clear">
            <p>This is a demo installation of Little Goblin, a fantasy game based upon the framework by the same name,
            which is currently under development. This test server will be reset with each update.
            </p>

            <p>You are invited to test the current functionality by using the "anon" login with password "anon".

            If you want to report any bugs or if you miss a specific feature, please file a
                <a href="https://sourceforge.net/tracker/?group_id=317345&amp;atid=1334697">bug report</a>
                or a
                <a href="https://sourceforge.net/tracker/?group_id=317345&amp;atid=1334694">feature request</a> on Sourceforge.net
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
                Version 0.4.0 "Upgraded" / 2014-09-07
                * Upgrade to Grails 2.4.3. May contain bugs.
                * AssetController is still work in progress.
            </pre>
            <pre>
                Version 0.3.4 "Users" / 2013-06-30
                * #4: Added / fixed UserController. Still a little rough, but works.
                * Upgraded to Grails 2.2.3
                * Code cleaning
                * Upgrade resources plugin to 1.2
                
                Version 0.3.3 "Fixtures"* / 2013-03-16

                * Improved documentation
                * Upgrade to Grails 2.2.1
                * Migrated game and system data of the reference implementation to fixtures,
                which allows you to edit them more easily.
                * Fixed: when creating a new order, initialize its main chat.

            </pre>
            
            <div class="">
                <a href="${resource(dir: '/', file: 'status.txt')}" target="_blank"><g:message code="link.to.status.old"/></a>
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
                    <noscript><g:submitButton name="changeLang" value="${message(code:'language.change')}"/></noscript>
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