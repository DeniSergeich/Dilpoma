FROM maven:3.8.4-openjdk-17 AS builder
WORKDIR /app
COPY ../../../.. /app/.
RUN mvn -f /app/pom.xml clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]