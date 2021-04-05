ARG NODE_VERSION=15.12
ARG ALPINE_VERSION=3.13

FROM node:${NODE_VERSION}-alpine${ALPINE_VERSION}

RUN mkdir /website/
WORKDIR /website/

COPY ./src/website/ /website/

RUN yarn install

CMD yarn debug
