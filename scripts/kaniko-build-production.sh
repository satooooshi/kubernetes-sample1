#!/usr/bin/env sh
set -eu

KANIKO_EXECUTOR='/kaniko/executor'
if type executor 2>&1 > /dev/null; then
  KANIKO_EXECUTOR=executor
fi
if ! ${KANIKO_EXECUTOR} version 2>&1 > /dev/null; then
  echo 'this script need kaniko' 1>&2
  exit 1
fi

# move to project root directory
cd "`dirname $0`/../"

export REPOSITORY="${REPOSITORY:+${REPOSITORY}/}"

if [ "$#" -lt 1 ]; then
  echo "$0: There are no argument for component." 1>&2
  exit 1
fi
export component="$1"

if [ "${REPOSITORY:-x}" == 'x' ]; then
  destination_opt="--destination ${REPOSITORY}${component}:prod"
else
  destination_opt="--no-push"
fi

"${KANIKO_EXECUTOR}" \
  ${destination_opt} \
  --dockerfile "${PWD}/package/${component}/production/Dockerfile" \
  --context "dir://${PWD}"
