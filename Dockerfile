FROM tomcat:9-jre8
LABEL authors="bg8"
RUN rm -r /usr/local/tomcat/webapps/
COPY ROOT.WAR /usr/local/tomcat/webapps/