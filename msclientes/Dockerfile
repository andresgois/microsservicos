FROM maven:3.9.5-sapmachine-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17
WORKDIR /app
# COPY ./target/eurekaserver-0.0.1-SNAPSHOT.jar app.jar
COPY --from=build ./app/target/*.jar ./app.jar

ARG EUREKA_SERVER=localhost
ENTRYPOINT java -jar -Dspring.profiles.active=prod app.jar