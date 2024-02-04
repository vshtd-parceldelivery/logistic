FROM gradle:8.5.0-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM eclipse-temurin:17-jdk-alpine
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/logistic.jar
CMD ["java", "-Xmx200m", "-jar", "/app/logistic.jar"]
ENV HOST=host.docker.internal
EXPOSE 8884
