#!/usr/bin/env bash
# shellcheck disable=SC2046
docker container rm $(docker container ls -a -q)
docker build -t riosun269/appium-android .

# get artifact
IMAGE="riosun269/appium-android"
CONTAINER="container_test"
echo killing all containers...
docker container rm $(docker container ls -a -q)
echo creating latest image
docker create --name $CONTAINER $IMAGE
#VAR=$(docker ps --format "{{.Names}}" -a)
docker cp $CONTAINER:/home/src/app/target/zip-with-dependencies.zip src/test/resources/zip-test/
echo get artifacts successfully!