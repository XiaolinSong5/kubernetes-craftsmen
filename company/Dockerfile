FROM maven:3.9.6-eclipse-temurin-17 as build

COPY . .

RUN mvn clean package

FROM eclipse-temurin:17-jre-jammy

WORKDIR /app
COPY --from=build /target/company-*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","/app/app.jar"]

