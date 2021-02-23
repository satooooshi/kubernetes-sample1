ARG JAVA_VERSION=11
ARG MAVEN_VERSION=3

FROM maven:${MAVEN_VERSION}-adoptopenjdk-${JAVA_VERSION}  AS builder

RUN mkdir /article/
WORKDIR /article/

COPY ./src/article/ /article/

RUN mvn package

FROM adoptopenjdk:${JAVA_VERSION}-jre-hotspot

ENV DEBUG false
ENV ADDITIONAL_ARGS ''
ENV SPRING_PROFILES_ACTIVE production

RUN mkdir /article/
WORKDIR /article/

COPY --from=builder /article/target/article.jar  /article/article.jar
COPY ./package/scripts/run-production.sh /run.sh

CMD /run.sh /article/article.jar
