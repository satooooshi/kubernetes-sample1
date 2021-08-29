#!/usr/bin/env sh
set -eu
if "${DEBUG:-false}"; then
  set -vx
fi

# move to project root directory
cd "`dirname $0`/../"

if docker compose version 2>&1 > /dev/null; then
  COMPOSE_COMMAND='docker compose'
elif type docker-compose 2>&1 > /dev/null; then
  COMPOSE_COMMAND='docker-compose'
else
  echo 'this script needs "compose" subcommand with "docker" cli, or "docker-compose".' 1>&2
  exit 1
fi

${COMPOSE_COMMAND} -f "./deployment/develop/docker/docker-compose.yaml" "$@"
