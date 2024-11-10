# Utilise l'image OpenJDK 11 depuis Docker Hub
FROM openjdk:11-jdk-slim

# Exposez le port de l'application Spring Boot (ajustez si nécessaire)
EXPOSE 8082

# Ajoutez le fichier JAR généré dans l'image
ADD target/tpFoyer-17-0.0.1-SNAPSHOT.jar /tp-foyer.jar

# Définissez la commande de lancement pour l'application
ENTRYPOINT ["java", "-jar", "/tp-foyer.jar"]
