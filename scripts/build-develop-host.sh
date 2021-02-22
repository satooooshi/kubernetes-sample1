#!/usr/bin/env sh
set -eu

# move to project root directory
cd "`dirname $0`/../"

if [ "$#" -lt 1 ]; then
  echo "$0: There are no argument for component." 1>&2
  exit 1
fi
export component="$1"

cd "src/${component}"
mvn package
