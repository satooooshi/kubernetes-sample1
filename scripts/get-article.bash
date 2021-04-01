#!/usr/bin/env bash
set -eu

export ARTICLE_URL="${ARTICLE_URL:-http://localhost:8081}"
export CURL_OPTS="${CURL_OPTS:--s}"
if [[ -v UserID ]]; then
  export UID_HEADER="-H 'Content-Type: ${UserID}'"
else
  export UID_HEADER=""
fi

curl ${CURL_OPTS} ${UID_HEADER} "${ARTICLE_URL}/api/articles/${1}"
