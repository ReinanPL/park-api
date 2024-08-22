FROM maven:3.8.6-eclipse-temurin-17-alpine AS build

COPY src /app/src
COPY pom.xml /app

WORKDIR /app
RUN mvn clean install

FROM openjdk:17-alpine

COPY --from=build /app/target/demo-park-api-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR /app

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]