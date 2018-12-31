#!/usr/bin/env bash

#scp -r Dockerfile root@39.106.151.167:/root/docker/websites/death-knight

cd dk-service

mvn clean

mvn package

scp -r ./target/death-knight-service-1.0-SNAPSHOT.jar root@39.106.151.167:/root/docker/websites/death-knight

# ssh -p 22 root@39.106.151.167
# cd /root/docker/websites/death-knight

# delete container and images
# ...

# docker build -t death-knight-service .
# docker run --name death-knight-service -d -p 8080:8080 death-knight-service

