ARG NODE_VERSION=15.12
ARG ALPINE_VERSION=3.13

FROM node:${NODE_VERSION}-alpine${ALPINE_VERSION} AS builder

RUN mkdir /website/
WORKDIR /website/

COPY ./src/website/ /website/

RUN yarn install \
    && yarn build

FROM node:${NODE_VERSION}-alpine${ALPINE_VERSION}

WORKDIR /website/

COPY ./src/website/ /website/
COPY --from=builder /website/node_modules /website/node_modules
COPY --from=builder /website/.nuxt /website/.nuxt

CMD yarn start
