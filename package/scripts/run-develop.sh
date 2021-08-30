#!/usr/bin/env sh
set -eu
if "${DEBUG:-false}"; then
  set -vx
fi

# additional(extra) arguments
export ADDITIONAL_ARGS="${ADDITIONAL_ARGS:-}"

# debugger config
export ENABLE_DEBUGGER="${ENABLE_DEBUGGER:-false}"
export DEBUGGER_PORT="${DEBUGGER_PORT:-8000}"
if ${ENABLE_DEBUGGER}; then
  debugger_args="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=${DEBUGGER_PORT}"
else
  debugger_args=''
fi
export debugger_args

# jar arguments
if [ "$#" -lt 1 ]; then
  echo "$0: There are no argument for jar path" 1>&2
  exit 1
fi
export jar_path="$1"

exec java ${debugger_args} ${ADDITIONAL_ARGS} -jar "${jar_path}"
