# Build app
FROM gradle:7.6-jdk11 as BUILD

COPY --chown=gradle:gradle . /appbuild
WORKDIR /appbuild
RUN gradle buildFatJar --no-daemon

# Configure container
FROM openjdk:11
EXPOSE 8080:8080
RUN mkdir /app
COPY --from=BUILD /appbuild/build/libs/*.jar /app/docker-sample.jar
ENTRYPOINT ["java","-jar","/app/docker-sample.jar"]
