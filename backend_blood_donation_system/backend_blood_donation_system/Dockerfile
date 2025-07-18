# Giai đoạn 1: Build ứng dụng với Maven và Java 21
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline
COPY src src
RUN ./mvnw package -DskipTests

# Giai đoạn 2: Chạy ứng dụng trên một môi trường Java 21 nhẹ hơn
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]