FROM openjdk:17-jdk-alpine

# Expose le port sur lequel votre application écout
EXPOSE 8082

# Étape 2: Installez curl dans l'image Alpine
RUN apk add --no-cache curl

# Télécharge le fichier JAR depuis Nexus
RUN curl -u admin:Sonatype2024 -o /tpFoyer-17-0.0.1.jar \
    http://192.168.1.235:8081/repository/maven-releases/tn/esprit/tpFoyer-17/0.0.1/tpFoyer-17-0.0.1.jar


# Commande d'entrée pour exécuter le JAr
ENTRYPOINT ["java", "-jar", "/tpFoyer-17-0.0.1.jar"]
