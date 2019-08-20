FROM gradle:jdk11-slim as builder

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test

FROM openjdk:11-jre-slim

COPY --from=builder /home/gradle/src/docstampr-api-rest/build/libs/*-api-rest*.jar api-rest.jar

EXPOSE 8889

ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /api-rest.jar" ]
