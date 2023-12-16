# Use a base image with OpenJDK 17 and Maven installed
FROM maven:3.8.4-openjdk-17 AS builder

# Set the working directory
WORKDIR /app

# Copy the project files
COPY . /app

# Build the application using Maven
RUN mvn clean install -P dev

# Use a lightweight base image with OpenJDK 17
FROM openjdk:17-jdk-alpine3.14
# Set the working directory
WORKDIR /app

COPY --from=builder /app/target/*.jar /app/core-service.jar

EXPOSE 8002

CMD ["java","-jar", "core-service.jar"]