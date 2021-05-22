FROM openjdk:11-jre-slim
EXPOSE 8301
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT [ "java","-Dspring.profiles.active=local", "-jar", "/app.jar" ]
