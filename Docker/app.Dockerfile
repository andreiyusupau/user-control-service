FROM openjdk:11-jdk
MAINTAINER nevermind.com
VOLUME /tmp
EXPOSE 8080
ADD build/libs/user-control-service-0.0.1-SNAPSHOT.jar user-control-service.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/user-control-service.jar"]