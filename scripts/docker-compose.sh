#!/usr/bin/env sh
set -eu
if "${DEBUG:-false}"; then
  set -vx
fi

# move to project root directory
cd "`dirname $0`/../"

docker-compose -f "./deployment/develop/docker/docker-compose.yaml" "$@"
