#!/usr/bin/env bash
set -eu

cd "$(dirname "${0}")"

export ARTICLES=($( \
  ./list-articles.bash \
    | jq -r '.[].id' \
    | tr -d '\r' \
))
export USERS=($( \
  seq "$(( ${#ARTICLES[@]} * 3 ))" \
    | awk '{ print "user" $0 }' \
))

export ACCESS_DATE="${1}"
export totalrank_random_shift="${RANDOM}"
export uniquerank_random_shift="${RANDOM}"

for aidx in "${!ARTICLES[@]}"; do

  export uniqueAccess="$(( ( ( ${aidx} + ${uniquerank_random_shift} ) % ${#USERS[@]}  + 1 ) ))"
  export scale_factor=3
  export totalAccess="$(( ( ( ( ${aidx} + ${totalrank_random_shift} ) % ${#USERS[@]}  + 1 ) * ${scale_factor} ) - ( ${RANDOM} % ${scale_factor} ) ))"
  if (( ${uniqueAccess} > ${totalAccess} )); then
    export totalAccess="${uniqueAccess}"
  fi

  echo "dummy access at ${ACCESS_DATE} to article ${ARTICLES[${aidx}]} with expected totalAccess: ${totalAccess}, and uniqueAccess: ${uniqueAccess}"

  for accidx in $(seq "${totalAccess}"); do

    export UserID="${USERS[$(( ${accidx} % ${uniqueAccess} ))]}"
    export accessAt="$( \
      printf '%sT%02d:%02d:%02d+09:00' \
        "${ACCESS_DATE}" \
        "$(( ${RANDOM} % 24 ))" \
        "$(( ${RANDOM} % 60 ))" \
        "$(( ${RANDOM} % 60 ))" \
    )"

    ./put-dummy-accesscount.bash \
      "${ARTICLES[${aidx}]}" \
      "${UserID}" \
      "${accessAt}" \
      > /dev/null &

  done

done
