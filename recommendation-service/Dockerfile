FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . .

COPY gradlew .
COPY gradlew.bat .
RUN ls -l /app
RUN chmod +x gradlew

RUN ./gradlew clean build -x test --info
RUN ./gradlew bootJar

CMD ["java", "-jar", "build/libs/recommendation-service-0.0.1-SNAPSHOT.jar"]