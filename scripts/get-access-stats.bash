#!/usr/bin/env bash
set -eu

export ACCESSCOUNT_URL="${ACCESSCOUNT_URL:-http://localhost:8082}"
export CURL_OPTS='-s'

if (( "$#" < 3 )); then
    cat << __EOT__ 1>&2
    Usage: $0 <article id> <from> < to >

        <article id>    article id
        <from>          start date of terms to stats calculating (including)
        <to>            end date of terms to stats calculating (excluding)
                        <from>/<to> are fomatted "YYYY-MM-DD"
__EOT__
fi

curl ${CURL_OPTS} "${ACCESSCOUNT_URL}/accesscounts/stats/?article=${1}&from=${2}T00:00:00%2B09:00&to=${3}T00:00:00%2B09:00"
