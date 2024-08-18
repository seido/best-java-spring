#!/bin/bash -eu

MY_DIR=$(cd $(dirname $0) && pwd)

cd $MY_DIR && docker compose run --rm -p 8080:8080 server