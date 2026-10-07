FROM harbor.cmt.apps.telenet.be/dockerhub/library/openjdk:11-jre-slim
ENV spring_profiles_active=docker
VOLUME /tmp
ARG APP_JAR="nba-adaptor-webapp/target/*.jar"
COPY ${APP_JAR} app.jar
EXPOSE 8071
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
