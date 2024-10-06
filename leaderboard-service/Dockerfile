FROM openjdk:17-jdk-slim

WORKDIR /app

COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
RUN ./gradlew clean build -x test --info

COPY src/ ./src
RUN ./gradlew bootJar

CMD ["java", "-jar", "build/libs/leaderboard-service-0.0.1-SNAPSHOT.jar"]