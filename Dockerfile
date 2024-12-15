# Build
FROM gradle:8.11.1-jdk21 AS build
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY src ./src
RUN gradle clean build

# Run
FROM openjdk:21-jdk
COPY --from=build /app/build/libs/*.jar /app/recommendation.jar
ENTRYPOINT ["java", "-jar", "/app/recommendation.jar"]