#!/usr/bin/env bash
set -eu

cd "$(dirname "${0}")"

export ARTICLES=($( \
  ./list-articles.bash \
    | jq -r '.[].id' \
    | tr -d '\r' \
))
export USERS=($( \
  seq "${#ARTICLES[@]}" \
    | awk '{ print "user" $0 }' \
))

for aidx in "${!ARTICLES[@]}"; do
  export totalAccess="$(( ( ${#ARTICLES[@]} - ${aidx} ) * ${#USERS[@]} ))"
  export uniqueAccess="$(( ${#USERS[@]} - ( ( ${aidx} + ${#USERS[@]} / 2 ) % ${#USERS[@]} ) ))"
  echo "access to article ${ARTICLES[${aidx}]} with expected totalAccess: ${totalAccess}, and uniqueAccess: ${uniqueAccess}"
  for accidx in $(seq "${totalAccess}"); do
    export UserID="${USERS[$(( ${accidx} % ${uniqueAccess} ))]}"
    ./get-article.bash "${ARTICLES[${aidx}]}" > /dev/null &
  done
done

wait
