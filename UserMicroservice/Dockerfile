FROM maven:3.8.1-jdk-11 AS build
COPY . /app
WORKDIR /app
RUN mvn package -DskipTests

FROM openjdk:18-jdk-slim
COPY --from=build /app/target/ds-user-microservice-0.0.1-SNAPSHOT.jar user-ms.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "user-ms.jar"]