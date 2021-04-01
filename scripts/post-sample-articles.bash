#!/usr/bin/env bash
set -eu

export ARTICLE_URL="${ARTICLE_URL:-http://localhost:8081}"
export CURL_OPTS='-s'

for i in {1..10}; do
    suffix="$(echo -n "${i}-$(date --iso-8601=ns)" | md5sum | base64 | sed -r -e 's/[^a-zA-Z]+//g' | head -c4)"
    suffix="${suffix,,}"
    title="Title - article title ${suffix} -"
    author="John ${suffix^} Doe"
    body="Very interest article here. (${suffix})"
    curl \
        ${CURL_OPTS} \
        -H 'Content-Type: application/json' \
        --data-binary '{"title":"'"${title}"'","author":"'"${author}"'","body":"'"${body}"'"}' \
        "${ARTICLE_URL}/api/articles/"
done
