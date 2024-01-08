#!/bin/bash

docker build -t "registry.lion7.dev/$USER/employee" "$(dirname $0)"
