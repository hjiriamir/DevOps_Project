# Use the OpenJDK 11 image from Docker Hub
FROM openjdk:11-jdk-slim

# Expose the port that the Spring Boot application runs on
EXPOSE 8082

# Add the JAR file to the Docker image
ADD target/tpFoyer-17-0.0.1-SNAPSHOT.jar tpFoyer-17-0.0.1-SNAPSHOT.jar

# Set the entry point for the container
ENTRYPOINT ["java", "-jar", "/tpFoyer-17-0.0.1-SNAPSHOT.jar"]
