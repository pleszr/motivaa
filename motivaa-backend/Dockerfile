FROM maven:3-eclipse-temurin-21 AS build
# Set the working directory in the container
WORKDIR /app
# Copy the pom.xml and the project files to the container
COPY ./motivaa-backend /app/
# Build the application using Maven
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
COPY --from=build /app/motivaa-app/target/original-motivaa-app-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev", "/app.jar"]