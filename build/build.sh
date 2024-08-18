#!/bin/bash -eu

MY_DIR=$(cd $(dirname $0) && pwd)

cd $MY_DIR && \
    docker compose run --rm e2e && \
    docker compose down
