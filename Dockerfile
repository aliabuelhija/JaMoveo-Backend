FROM ubuntu:latest AS build

# Install necessary dependencies
RUN apt-get update && apt-get install openjdk-17-jdk -y

# Set the working directory to /app
WORKDIR /app

# Copy all files from the current directory into the Docker image
COPY . .

# Ensure gradlew is executable
RUN chmod +x gradlew

# Build the application
RUN ./gradlew bootJar --no-daemon

# Use a lightweight JDK image for the final build
FROM openjdk:17-jdk-slim

# Expose port 8080
EXPOSE 8080

# Copy the built jar from the build stage to the final stage
COPY --from=build /app/build/libs/JaMoveo-1.jar app.jar

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
