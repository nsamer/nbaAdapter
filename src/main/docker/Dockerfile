ARG CI_HARBOR_REGISTRY
FROM ${CI_HARBOR_REGISTRY}/dockerhub/library/amazoncorretto:21
ARG APPJAR=${CI_PROJECT_DIR}/nba-adaptor-webapp/target/nba-adaptor-app.jar
COPY ${APPJAR} app.jar
ENTRYPOINT java ${JAVA_OPTS}  -jar app.jar ${APP_OPTS}
