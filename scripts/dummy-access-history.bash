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

function last_month_start_day() {
  base="$1"
  base_year="$( sed -r -e 's/^0*([1-9][0-9]*)-.*$/\1/' <<<"${base}" )"
  base_month="$( sed -r -e 's/^[^-]*-0?(.*)-[^-]*$/\1/' <<<"${base}" )"
  base_date="$( sed -r -e 's/^[^-]*-[^-]*-0?(.*)$/\1/' <<<"${base}" )"
  if (( ${base_month} == 1 )); then
    last_year="$(( "${base_year}" - 1 ))"
    last_month=12
  else
    last_year="${base_year}"
    last_month="$(( "${base_month}" - 1 ))"
  fi
  last_date=1
  printf '%04d-%02d-%02d\n' "${last_year}" "${last_month}" "${last_date}"
}

function dayseq() {
  start="$1"
  start_year="$( sed -r -e 's/^0*([1-9][0-9]*)-.*$/\1/' <<<"${start}" )"
  start_month="$( sed -r -e 's/^[^-]*-0?(.*)-[^-]*$/\1/' <<<"${start}" )"
  start_date="$( sed -r -e 's/^[^-]*-[^-]*-0?(.*)$/\1/' <<<"${start}" )"
  end="$2"
  end_year="$( sed -r -e 's/^0*([1-9][0-9]*)-.*$/\1/' <<<"${end}" )"
  end_month="$( sed -r -e 's/^[^-]*-0?(.*)-[^-]*$/\1/' <<<"${end}" )"
  end_date="$( sed -r -e 's/^[^-]*-[^-]*-0?(.*)$/\1/' <<<"${end}" )"

  current_year="${start_year}"
  current_month="${start_month}"
  current_date="${start_date}"
  while (( "$( printf '1%04d%02d%02d' "${current_year}" "${current_month}" "${current_date}" )" <= "$( printf '1%04d%02d%02d' "${end_year}" "${end_month}" "${end_date}" )" )); do
    printf '%04d-%02d-%02d\n' "${current_year}" "${current_month}" "${current_date}"

    current_date="$(( ${current_date} + 1 ))"
    if ! date --date "${current_year}-${current_month}-${current_date}" &> /dev/null; then
      current_month="$(( "${current_month}" + 1 ))"
      current_date=1
    fi
    if ! date --date "${current_year}-${current_month}-${current_date}" &> /dev/null; then
      current_year="$(( "${current_year}" + 1 ))"
      current_month=1
      current_date=1
    fi
  done
}

export TODAY="$( date +'%Y-%m-%d' )"
export START="$( last_month_start_day "${TODAY}" )"
export dateIndex=0
for current_date in $(dayseq "${START}" "${TODAY}"); do
  export random_shift="${RANDOM}"
  for aidx in "${!ARTICLES[@]}"; do

    export uniqueAccess="$(( ( ( ${aidx} + ${dateIndex} ) % ${#USERS[@]}  + 1 ) ))"
    export scale_factor=10
    export totalAccess="$(( ( ( ( ${aidx} + ${dateIndex} + ${random_shift} ) % ${#USERS[@]}  + 1 ) * ${scale_factor} ) - ( ${RANDOM} % ${scale_factor} ) ))"

    echo "dummy access at ${current_date} to article ${ARTICLES[${aidx}]} with expected totalAccess: ${totalAccess}, and uniqueAccess: ${uniqueAccess}"

    for accidx in $(seq "${totalAccess}"); do

      export UserID="${USERS[$(( ${accidx} % ${uniqueAccess} ))]}"
      export accessAt="$( \
        printf '%sT%02d:%02d:%02d+09:00' \
          "${current_date}" \
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
  export dateIndex="$(( ${dateIndex} + 1 ))"

done
