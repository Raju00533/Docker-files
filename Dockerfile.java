# Use the base image for the java 

FROM maven:3.8.6-openjdk-17 AS build

# Set the working directory

WORKDIR /app

# copy the pom.xml and download depencies

COPY pom.xml .

COPY src ./src

# Build the application

RUN mvn clean package -DskipTests

# Use an Official Openjdk image as a base for the final image

FROM openjdk:17-jdk-slim AS runtime

# Set the working directory

WORKDIR /app

# copy the jar file from the build stage

COPY --from=build /app/target/myapp.jar ./myapp.jar

# Expose the port 

EXPOSE 8080

# Specify the command to run the application

ENTRYPOINT ["java", "-jar", "myapp.jar"]




