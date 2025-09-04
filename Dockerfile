FROM maven:3.8.5-openjdk-17 AS build
COPY /src /usr/app/src
COPY pom.xml /usr/app/
RUN mvn -f /usr/app/pom.xml clean package

FROM openjdk:17-jdk-slim
WORKDIR /usr/app
COPY --from=build /usr/app/target/*.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]
