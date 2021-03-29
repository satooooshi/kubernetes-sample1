#!/usr/bin/env bash
set -eu

export ARTICLE_URL="${ARTICLE_URL:-http://localhost:8081}"
export CURL_OPTS="${CURL_OPTS:--s}"
export UserID="${UserID:-${USER}}"

curl ${CURL_OPTS} -H "X-UID: ${UserID}" "${ARTICLE_URL}/articles/${1}"
