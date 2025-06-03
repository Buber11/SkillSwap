# Użyj lekkiego obrazu JDK jako bazowego
FROM eclipse-temurin:21-jdk-jammy

# Ustaw katalog roboczy
WORKDIR /app

# Skopiuj plik JAR do kontenera
COPY target/SkillSwap-0.0.1-SNAPSHOT.jar app.jar

# Otwórz port (jeśli potrzebny)
EXPOSE 8081

# Komenda startowa
ENTRYPOINT ["java", "-jar", "app.jar"]
