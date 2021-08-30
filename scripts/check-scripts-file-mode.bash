#!/usr/bin/env bash
set -eu

scripts_dirs=(
  './scripts'
  './package/scripts'
)

# To make revert return code of `grep` command, place '!'.
! find \
  "${scripts_dirs[@]}" \
  -maxdepth 1 \
  -name '*.sh' \
  -or \
  -name '*.bash' \
  | xargs stat -c '%a  %n' \
  | grep -v '755'
