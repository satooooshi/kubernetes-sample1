#!/usr/bin/env sh
set -eu
if "${DEBUG:-false}"; then
  set -vx
fi

if [ "$#" -lt 1 ]; then
  echo "$0: There are no argument for component." 1>&2
  exit 1
fi
export component="$1"

cd "`dirname $0`/../src/${component}"

shift

./mvnw "$@"
