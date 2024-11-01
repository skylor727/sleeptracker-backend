# Stage 1: Build
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/sleeptracker-*.jar sleeptracker-backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "sleeptracker-backend.jar"]
