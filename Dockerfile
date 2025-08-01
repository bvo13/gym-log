FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src ./src

RUN chmod +x mvnw
RUN ./mvnw clean package -Dmaven.test.skip=true

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/gymloggingapp-0.0.1-SNAPSHOT.jar"]