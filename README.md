# Release notes for Little Goblin v 0.5.2

Little Goblin is a browser game framework written with the Grails framework
using Groovy, Java and JavaScript. It includes a reference implementation
for testing and demonstration which will create a basic usable
game environment as seen on the test server on http://schedim.de

## Getting started

For instructions to install the binary version, see: [INSTALL.md](INSTALL.md)

* Checkout the source code from Github:

    git clone git@github.com:dewarim/LittleGoblin.git

* Edit goblin-config.example.groovy according to your database settings and serverURL

* Start the application with

    grails run-app

* Visit http://localhost:8080/goblin after the startup phase has completed.

## Further steps

You can include Little Goblin as a plugin in your own Grails application.
In BuildConfig, just add 

    compile ':goblin:0.5.2'

to the plugins section. 

Then you can adjust the CSS, layout and overall design as well as the
inner game mechanics. If you have any questions or want to contribute,
do not hesitate to ask or raise an issue.

## License

This software is licensed under the Apache License 2.0.
Open source components contained therein may have different
(but generally compatible) Licenses like LGPL or BSD.

This is a Grails 2.4.4 application running on Java 8.

This code may link to images on the web which have their own copyright
 and licenses, which you should consider and review before going
 public with a game based upon LittleGoblin. Generally, it's best
 if you use your own images (and the ones supplied inside the WAR
 file).

## Links and Contact

* Project Admin: Ingo Wiarda / ingo_wiarda@dewarim.de
* Demo Website: http://schedim.de
* Documentation: http://littlegoblin.de
* GitHub page: https://github.com/dewarim/LittleGoblin
* Issue Tracker: https://github.com/dewarim/LittleGoblin/issues
* Blog for LittleGoblin: http://dewarim.com
