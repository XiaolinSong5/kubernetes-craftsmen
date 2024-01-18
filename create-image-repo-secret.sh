#!/bin/bash

kubectl create secret generic regcred \
    --from-file=.dockerconfigjson=./config.json \
    --type=kubernetes.io/dockerconfigjson
