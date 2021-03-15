ARG JAVA_VERSION=11
ARG MAVEN_VERSION=3

FROM maven:${MAVEN_VERSION}-adoptopenjdk-${JAVA_VERSION}  AS builder

RUN mkdir /accesscount/
WORKDIR /accesscount/

COPY ./src/accesscount/pom.xml /accesscount/pom.xml
RUN mvn dependency:go-offline

COPY ./src/accesscount/ /accesscount/
RUN mvn compile
RUN mvn compiler:testCompile
RUN mvn package

ENV DEBUG false
ENV ADDITIONAL_ARGS ''
ENV ENABLE_DEBUGGER false
ENV DEBUGGER_PORT 8000
ENV SPRING_PROFILES_ACTIVE develop

COPY ./package/scripts/run-develop-docker.sh /run.sh

CMD /run.sh accesscount
