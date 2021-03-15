#!/usr/bin/env sh
set -eu
if "${DEBUG:-false}"; then
  set -vx
fi

# move to project root directory
cd "`dirname $0`/../"

if [ "$#" -lt 1 ]; then
  echo "$0: There are no argument for component." 1>&2
  exit 1
fi
export component="$1"

# additional(extra) arguments
export ADDITIONAL_ARGS="${ADDITIONAL_ARGS:-}"

arguments="`echo -n "${ADDITIONAL_ARGS}" | sed -r 's/ /,/g'`"
if [ "${arguments}" ]; then
  arguments="-Dspring-boot.run.arguments=${arguments}"
fi
export arguments

# debugger config
export ENABLE_DEBUGGER="${ENABLE_DEBUGGER:-false}"
export DEBUGGER_PORT="${DEBUGGER_PORT:-8000}"
if "${ENABLE_DEBUGGER}"; then
  debugger_args="-Dspring-boot.run.jvmArguments='-Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=${DEBUGGER_PORT}'"
else
  debugger_args=''
fi
export debugger_args

export SPRING_PROFILES_ACTIVE=develop-host
cd "./src/${component}"
mvn ${arguments} ${debugger_args} spring-boot:run