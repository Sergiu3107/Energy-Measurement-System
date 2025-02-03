FROM maven:3.8.1-jdk-11 AS build
COPY . /app
WORKDIR /app
RUN mvn package -DskipTests

FROM openjdk:18-jdk-slim
COPY --from=build /app/target/ds-chat-microservice-0.0.1-SNAPSHOT.jar chat-ms.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "chat-ms.jar"]