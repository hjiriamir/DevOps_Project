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

# Update apt-get and install curl
RUN apt-get update && \
    apt-get install -y curl && \
    rm -rf /var/lib/apt/lists/*

# Verify that arguments are correctly passed by echoing them
RUN echo "Downloading artifact from Nexus with the following details:" && \
    echo "NEXUS_URL: ${NEXUS_URL}" && \
    echo "NEXUS_REPO: ${NEXUS_REPO}" && \
    echo "GROUP_ID: ${GROUP_ID}" && \
    echo "ARTIFACT_ID: ${ARTIFACT_ID}" && \
    echo "VERSION: ${VERSION}" && \
    echo "NEXUS_USERNAME: ${NEXUS_USERNAME}"

# Download the JAR file from Nexus
RUN curl -v -u "${NEXUS_USERNAME}:${NEXUS_PASSWORD}" \
    -o "${ARTIFACT_ID}-${VERSION}.jar" \
    "${NEXUS_URL}/repository/${NEXUS_REPO}/$(echo ${GROUP_ID} | tr '.' '/')/${ARTIFACT_ID}/${VERSION}/${ARTIFACT_ID}-${VERSION}.jar"

# Set the entry point for the container
ENTRYPOINT ["java", "-jar", "/tpFoyer-17-0.0.1-SNAPSHOT.jar"]
