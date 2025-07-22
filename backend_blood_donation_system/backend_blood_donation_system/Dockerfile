# Giai đoạn 1: Build ứng dụng
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Trỏ đường dẫn vào thư mục con chứa code
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src src

RUN mvn package -DskipTests

# Giai đoạn 2: Chạy ứng dụng
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]