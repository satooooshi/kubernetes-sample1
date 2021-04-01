#!/usr/bin/env bash
set -eu

export ARTICLE_URL="${ARTICLE_URL:-http://localhost:8081}"
export CURL_OPTS='-s'

curl ${CURL_OPTS} "${ARTICLE_URL}/api/articles/"
