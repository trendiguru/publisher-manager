# publisher-manager

Deploying Changes:

1.  Make changes
2.  `sudo su Jeremy && cd ~/publisher-manager`
3.  `git pull`
4.  `/home/Jeremy/apache-maven-3.3.9/bin/mvn package`
5.  `sudo cp -r target/publisher-ui-0.0.1-SNAPSHOT/* /var/lib/tomcat/apache-tomcat-7.0.68/webapps/publisher-manager`
  * There's definitely a better way to do this (like using the generated .war) but wasn't working for some reason. WARNING this only overrides existing files...
6.  `sudo /var/lib/tomcat/apache-tomcat-7.0.68/bin/shutdown.sh`
7.  `sudo /var/lib/tomcat/apache-tomcat-7.0.68/bin/startup.sh`
