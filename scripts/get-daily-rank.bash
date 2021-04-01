#!/usr/bin/env bash
set -eu

export RANK_URL="${RANK_URL:-http://localhost:8083}"
export CURL_OPTS="${CURL_OPTS:--s}"

curl ${CURL_OPTS} "${RANK_URL}/api/ranks/daily/?date=${1}"
