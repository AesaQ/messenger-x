FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . .

COPY gradlew .
COPY gradlew.bat .

RUN chmod +x gradlew
RUN ls -l /app
RUN ./gradlew clean build -x test --info
RUN ./gradlew bootJar

CMD ["java", "-jar", "build/libs/card-service-0.0.1-SNAPSHOT.jar"]