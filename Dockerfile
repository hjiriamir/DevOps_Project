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

# Update and install curl
RUN apt-get update && \
    apt-get install -y curl && \
    rm -rf /var/lib/apt/lists/*

# Display the URL and check network connectivity
RUN echo "Constructing Nexus URL for artifact download:" && \
    echo "URL: ${NEXUS_URL}/repository/${NEXUS_REPO}/$(echo ${GROUP_ID} | tr '.' '/')/${ARTIFACT_ID}/${VERSION}/${ARTIFACT_ID}-${VERSION}.jar" && \
    curl -I "${NEXUS_URL}" || echo "Nexus URL is not reachable"

# Download the JAR file from Nexus
RUN curl -v -u "${NEXUS_USERNAME}:${NEXUS_PASSWORD}" \
    -o "${ARTIFACT_ID}-${VERSION}.jar" \
    "${NEXUS_URL}/repository/${NEXUS_REPO}/$(echo ${GROUP_ID} | tr '.' '/')/${ARTIFACT_ID}/${VERSION}/${ARTIFACT_ID}-${VERSION}.jar"

# Set the entry point for the container
ENTRYPOINT ["java", "-jar", "/tpFoyer-17-0.0.1-SNAPSHOT.jar"]
