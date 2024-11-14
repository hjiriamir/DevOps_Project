# Utilisation de l'image OpenJDK 8 au lieu de OpenJDK 17
FROM openjdk:8-jdk-alpine

# Expose le port sur lequel votre application écoute
EXPOSE 8082

# Installe curl dans l'image Alpine pour télécharger le fichier JAR
RUN apk add --no-cache curl

# Télécharge le fichier JAR depuis Nexus
RUN curl -u admin:Sonatype2024 -o /tpFoyer-17-0.0.1.jar \
    http://192.168.100.8:8081/repository/maven-releases/tn/esprit/tpFoyer-17/0.0.1/tpFoyer-17-0.0.1.jar

# Commande d'entrée pour exécuter le JAR
ENTRYPOINT ["java", "-jar", "/tpFoyer-17-0.0.1.jar"]
