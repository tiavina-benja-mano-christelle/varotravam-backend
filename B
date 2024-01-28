FROM openjdk:17-jdk-slim-buster

WORKDIR /app

COPY target/varotravam-0.0.1-SNAPSHOT.jar /app/mon-projet.jar

EXPOSE 8080

CMD ["java", "-jar", "mon-projet.jar"]
