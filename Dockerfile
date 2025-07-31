FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/ct-0.0.1-SNAPSHOT.jar /app/ct.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/ct.jar"]
