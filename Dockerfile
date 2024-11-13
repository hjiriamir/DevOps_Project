FROM openjdk:17-slim
ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} tpFoyer-17-1.0.0.jar
ENTRYPOINT ["java", "-jar" ,"/tpFoyer-17-1.0.0.jar"]
EXPOSE 8082