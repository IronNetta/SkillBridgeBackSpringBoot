# Dockerfile à la racine du projet multi-module
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY api/pom.xml ./api/
COPY bll/pom.xml ./bll/
COPY dal/pom.xml ./dal/
COPY dl/pom.xml ./dl/
COPY il/pom.xml ./il/

# Résout les dépendances sans tout copier (cache Maven)
RUN mvn dependency:go-offline

COPY . .

# Compile seulement le module api
RUN mvn clean install -pl api -am -DskipTests

FROM eclipse-temurin:17-jdk-jammy AS runtime

WORKDIR /app

COPY --from=build /app/api/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
