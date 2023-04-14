#build stage
FROM maven:3.8.5-jdk-11-slim AS build

COPY src /home/app/src
COPY pom.xml /home/app/pom.xml

RUN mvn -f /home/app/pom.xml clean package

#run stage
FROM openjdk:11-jre-slim

COPY --from=build /home/app/target/SafariZoneBackEnd-0.0.1-SNAPSHOT.jar /home/app/App.jar

EXPOSE 9000

ENTRYPOINT ["java", "-jar", "/home/app/App.jar"]