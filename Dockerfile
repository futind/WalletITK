# build

FROM gradle:9.0-jdk17 AS build
WORKDIR /app

COPY build.gradle settings.gradle ./
COPY src ./src

RUN gradle clean build -x test --no-daemon

# runtime
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/build/libs/WalletITK-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]