FROM openjdk:8-jdk-alpine

COPY ./build/libs/*jar app.jar

ENTRYPOINT ["java", "-jar", "-Dserver.port=8000", "-Dspring.profiles.active=production", "/app.jar"]