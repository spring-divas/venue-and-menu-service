FROM gradle:9.7.1-jdk25-alpine AS build
WORKDIR /workdir

COPY build.gradle.kts settings.gradle.kts ./
COPY src ./src

RUN gradle bootJar --no-daemon -x test
RUN mkdir -p /pdf-storage

FROM gcr.io/distroless/java25-debian13:nonroot
WORKDIR /workdir

COPY --from=build /workdir/build/libs/*-SNAPSHOT.jar app.jar
COPY --from=build --chown=10001:10001 /pdf-storage/ /workdir/menu/

EXPOSE 8080
USER 10001:10001
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]