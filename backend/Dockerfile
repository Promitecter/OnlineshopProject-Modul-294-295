# Stage 1: Build der Applikation
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Wir überspringen Tests beim Build im Container, um Zeit zu sparen
RUN mvn clean package -DskipTests

# Stage 2: Ausführen der Applikation (nur JRE nötig)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Kopiere das fertige JAR-File aus dem Build-Container
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]