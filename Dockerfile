# Use official Java runtime
FROM openjdk:17-jdk-slim

# Set working directory in the container
WORKDIR /app

# Copy the compiled JAR file
COPY target/fintech-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 8094

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
