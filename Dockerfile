FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/command-service*.jar /app/command-service.jar
CMD ["java", "-jar", "command-service.jar"]