# Use Java 17 base image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy the JAR file from Maven target directory
COPY target/loop-0.0.1-SNAPSHOT.jar app.jar

# Expose the port (Cloud Run uses this environment variable automatically)
EXPOSE 8080

# Tell Spring Boot to use the Cloud Run port
ENV PORT=8080
ENTRYPOINT ["java", "-jar", "app.jar"]