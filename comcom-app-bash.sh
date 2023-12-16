#!/bin/bash
# Firstly Stops containers and removes containers, networks, volumes
# Build the Auth and Gateway services in production mode
(
  cd api-gateway/ || exit
  ./build-api-gateway.sh
)
(
  cd auth-service/ || exit
  ./build-auth-service.sh
)
(
  cd core-service/ || exit
  ./build-core-service.sh
)
docker compose down
docker compose build
docker compose up
