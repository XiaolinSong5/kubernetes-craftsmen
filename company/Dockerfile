FROM maven:3.9.6-eclipse-temurin-17 as build

COPY pom.xml pom.xml
RUN mvn dependency:go-offline

COPY . .

RUN mvn clean package

FROM eclipse-temurin:17-jre-jammy


WORKDIR /home/user
USER 1000:1000

COPY --from=build /target/company-*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","/home/user/app.jar"]

