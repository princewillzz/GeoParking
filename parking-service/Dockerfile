FROM openjdk:11-jre-slim
EXPOSE 8300
COPY target/parking-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT [ "java","-Dspring.profiles.active=local", "-jar", "/app.jar" ]
