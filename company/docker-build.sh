#!/bin/bash

docker build -t "registry.lion7.dev/$USER/company" "$(dirname $0)" --platform=linux/amd64
