FROM maven:3.8.1-jdk-11 AS build
COPY . /app
WORKDIR /app
RUN mvn package -DskipTests

FROM openjdk:18-jdk-slim
COPY --from=build /app/target/ds-device-microservice-0.0.1-SNAPSHOT.jar device-ms.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "device-ms.jar"]