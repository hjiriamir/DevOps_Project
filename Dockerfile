# Use the OpenJDK 17 image from Docker Hub
FROM openjdk:17-jdk-slim

# Expose the port that the Spring Boot application runs on
EXPOSE 8082

# Define build arguments for Nexus credentials and artifact details
ARG NEXUS_URL
ARG NEXUS_REPO
ARG GROUP_ID
ARG ARTIFACT_ID
ARG VERSION
ARG NEXUS_USERNAME
ARG NEXUS_PASSWORD

# Create a directory for the application
WORKDIR /app

# Download the JAR from Nexus
RUN curl -u $NEXUS_USERNAME:$NEXUS_PASSWORD -o app.jar "$NEXUS_URL/repository/$NEXUS_REPO/$(echo $GROUP_ID | tr '.' '/')/$ARTIFACT_ID/$VERSION/$ARTIFACT_ID-$VERSION.jar"

# Set the entry point for the container
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
