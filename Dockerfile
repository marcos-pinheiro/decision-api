FROM gradle:6.4.1-jdk8 AS builder

LABEL maintainer="mark"

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --stacktrace --info

FROM openjdk:8u212-jdk-alpine3.9
COPY --from=builder /home/gradle/src/build/libs/decision-engine-*-all.jar decision-engine.jar

EXPOSE 8080
ENTRYPOINT java -server -Xms2048m -Xmx2048m -Dcom.sun.management.jmxremote -Ddd.integration.netty.enabled=true -noverify ${JAVA_OPTS} -jar decision-engine.jar