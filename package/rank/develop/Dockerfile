ARG JAVA_VERSION=11
ARG MAVEN_VERSION=3

FROM maven:${MAVEN_VERSION}-adoptopenjdk-${JAVA_VERSION}  AS builder

RUN mkdir /rank/
WORKDIR /rank/

COPY ./src/rank/ /rank/

RUN mvn package

FROM adoptopenjdk:${JAVA_VERSION}-jre-hotspot

ENV DEBUG false
ENV ADDITIONAL_ARGS ''
ENV ENABLE_DEBUGGER false
ENV DEBUGGER_PORT 8000
ENV SPRING_PROFILES_ACTIVE develop

RUN mkdir /rank/
WORKDIR /rank/

COPY --from=builder /rank/target/rank.jar  /rank/rank.jar
COPY ./package/scripts/run-develop.sh /run.sh

CMD /run.sh /rank/rank.jar
