
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY  target/events.jar /app/events.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","events.jar"]