# Stage 1: Build the application
FROM gradle:8.5-jdk21 AS builder
WORKDIR /app

# Copy gradle files for dependency caching
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
RUN chmod +x gradlew
RUN ./gradlew build --no-daemon -x test || return 0

# Copy source and build JAR
COPY src ./src
RUN ./gradlew bootJar --no-daemon -x test

# Stage 2: Runtime image (Java 21 JRE)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the JAR from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]