#!/usr/bin/env bash
set -eu

export ACCESSCOUNT_URL="${ACCESSCOUNT_URL:-http://localhost:8082}"
export CURL_OPTS="${CURL_OPTS:--s}"

export BODY="{\"articleId\":\"${1}\",\"uid\":\"${2}\",\"accessAt\":\"${3}\"}"
curl ${CURL_OPTS} -H 'Content-Type: application/json' --data-binary "${BODY}" "${ACCESSCOUNT_URL}/api/accesscounts/"
