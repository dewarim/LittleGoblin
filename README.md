# Release notes for Little Goblin v 0.3.2

Little Goblin is a browser game framework written with the Grails framework
using Groovy, Java and JavaScript. It includes a reference implementation
for testing and demonstration which will create a basic usable
game environment as seen on the test server on http://schedim.de

## Getting started

For instructions to install the binary version, see: [INSTALL.md](INSTALL.md)
 
* Checkout the source code from Github:
 
    git git@github.com:dewarim/LittleGoblin.git
 
* Edit goblin-config.example.groovy according to your database settings and serverURL
 
* Start the application with 
    
    grails run-app

* Visit http://localhost:8080/goblin after the startup phase has completed.

## Further steps

You can include Little Goblin as a plugin in your own Grails application.
In BuildConfig, just add ":goblin:0.3.2" to the plugin section.*

    * note: this will work once the plugin is released.

Then you can adjust the CSS, layout and overall design as well as the
inner game mechanics. If you have any questions or want to contribute,
do not hesitate to ask or raise an issue.

## License

This software is licensed under the Apache License 2.0.
Open source components contained therein may have different
(but generally compatible) Licenses like LGPL or BSD.

This is a Grails 2.2 application running on Java 7.

This code may link to images on the web which have their own copyright
 and licenses, which you should consider and review before going
 public with a game based upon LittleGoblin. Generally, it's best
 if you use your own images (and the ones supplied inside the WAR
 file).

## Links and Contact

Project Admin: Ingo Wiarda / ingo_wiarda@dewarim.de
Demo Website: http://schedim.de
Blog for LittleGoblin: http://dewarim.com
Documentation: http://littlegoblin.de
Sourceforge page: http://sourceforge.net/projects/littlegoblin
GitHub page: https://github.com/dewarim/LittleGoblin
Issue Tracker: https://github.com/dewarim/LittleGoblin/issues
