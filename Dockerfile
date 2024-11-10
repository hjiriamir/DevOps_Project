

# Use the OpenJDK 11 image from Docker Hub
FROM openjdk:11-jdk-slim

# Expose the Spring Boot port (adjust if different)
EXPOSE 8082

# Add your application JAR to the image
ADD target/tp-foyer-1.0.jar tp-foyer.jar

# Define the entrypoint command to run your app
ENTRYPOINT ["java", "-jar", "/tp-foyer.jar"]

