

# Use the OpenJDK 11 image from Docker Hub
FROM openjdk:11-jdk-slim

# Expose the Spring Boot port (adjust if different)
EXPOSE 8082

# Add your application JAR to the image
ADD target/tpFoyer-17-0.0.1.jar tp-foyer.jar

# Define the entrypoint command to run your app
ENTRYPOINT ["java", "-jar", "/tp-foyer.jar"]

