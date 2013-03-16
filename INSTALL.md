# Installation instructions for the binary package of LittleGoblin

Download the WAR file from: [http://download.littlegoblin.de/files/current/goblin.war]

## Java

Download and install the Java 7 runtime from http://java.com/

On Ubuntu: sudo apt-get install openjdk-7-jre

## Tomcat 7

Download and install Tomcat 7 from http://tomcat.apache.org

On Ubuntu, use: sudo apt-get install tomcat7

## Database

* Download a database and configure it, either:
  [http://mysql.com] or [http://postgresql.org]

* Create a database "goblin" with user "goblin" and password "goblin" (for testing)

* Download a database driver for MySQL if needed
  [http://dev.mysql.com/downloads/connector/j/5.1.html]
  Put the mysql driver .jar-file into Tomcat's "lib" directory
  (Postgres JDBC driver is included in the war file)

* To edit your database settings, create in you home directory a folder .grails
 and inside a file goblin-config.groovy. You can download an example version from GitHub: https://github.com/dewarim/LittleGoblin/blob/master/goblin-config.example.groovy

## Running

* stop Tomcat
* copy the war file to the webapps directory of the Tomcat server.
* restart Tocmat
* visit http://localhost:8080/goblin

## Troubleshooting

If you have any questions, you can:
* Ask on the mailing list https://sourceforge.net/mailarchive/forum.php?forum_name=littlegoblin-main
* Use the forum (Be the first!): https://sourceforge.net/projects/littlegoblin/forums/forum/1129360
* Write me a mail ingo_wiarda@dewarim.de
