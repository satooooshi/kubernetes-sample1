#!/usr/bin/env sh
set -eu
if "${DEBUG:-false}"; then
  set -vx
fi

# additional(extra) arguments
export ADDITIONAL_ARGS="${ADDITIONAL_ARGS:-}"

# jar arguments
if [ "$#" -lt 1 ]; then
  echo "$0: There are no argument for jar path" 1>&2
  exit 1
fi
export jar_path="$1"

exec java ${ADDITIONAL_ARGS} -jar "${jar_path}"
