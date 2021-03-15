ARG JAVA_VERSION=11
ARG MAVEN_VERSION=3

FROM maven:${MAVEN_VERSION}-adoptopenjdk-${JAVA_VERSION}  AS builder

RUN mkdir /accesscount/
WORKDIR /accesscount/

COPY ./src/accesscount/ /accesscount/

RUN mvn package

FROM adoptopenjdk:${JAVA_VERSION}-jre-hotspot

ENV DEBUG false
ENV ADDITIONAL_ARGS ''
ENV SPRING_PROFILES_ACTIVE production

RUN mkdir /accesscount/
WORKDIR /accesscount/

COPY --from=builder /accesscount/target/accesscount.jar  /accesscount/accesscount.jar
COPY ./package/scripts/run-production.sh /run.sh

CMD /run.sh /accesscount/accesscount.jar
