# Use the OpenJDK 17 image from Docker Hub
FROM openjdk:17-jdk-slim

# Accept build arguments for Nexus credentials and artifact details
ARG NEXUS_URL
ARG NEXUS_REPO
ARG GROUP_ID
ARG ARTIFACT_ID
ARG VERSION
ARG NEXUS_USERNAME
ARG NEXUS_PASSWORD

# Expose the port that the Spring Boot application runs on
EXPOSE 8082

# Download the JAR file from Nexus instead of copying from the target folder
RUN apt-get update && apt-get install -y curl \
    && curl -u ${NEXUS_USERNAME}:${NEXUS_PASSWORD} \
    -o ${ARTIFACT_ID}-${VERSION}.jar \
    ${NEXUS_URL}/repository/${NEXUS_REPO}/${GROUP_ID.replace('.', '/')}/${ARTIFACT_ID}/${VERSION}/${ARTIFACT_ID}-${VERSION}.jar

# Set the entry point for the container
ENTRYPOINT ["java", "-jar", "/tpFoyer-17-0.0.1-SNAPSHOT.jar"]
