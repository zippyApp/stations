# Start with a base image containing Java runtime
FROM maven:3.9.5-eclipse-temurin-21-alpine AS build

# The application's .jar file
ARG JAR_FILE=target/*.jar

# cd into the project directory
WORKDIR /usr/src/app

# Copy the pom.xml file
COPY pom.xml .

# Build all dependencies for offline use (including plugins)
RUN mvn dependency:go-offline -B

# Copy your other files
COPY src src

# Build the project and skip tests
RUN mvn package -DskipTests

FROM openjdk:21

# Arguments
ARG DEPENDENCY=/usr/src/app/target

# Copy the application's .jar to the container
COPY --from=build ${DEPENDENCY}/*.jar app.jar

# Copy the wait-for-it.sh script to the container
COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh


# Run the application
ENTRYPOINT ["./wait-for-it.sh", "mydatabase:5432","--","java", "-jar", "/app.jar"]