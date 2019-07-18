#!/usr/bin/env bash

mvn clean & mvn package -DskipTests=true

scp -r dk-service/target/death-knight-service-1.0-SNAPSHOT.jar root@39.106.151.167:/root/docker/websites/death-knight

scp -r ./Dockerfile root@39.106.151.167:/root/docker/websites/death-knight

ssh -p 22 root@39.106.151.167 << eeooff

cd /root/docker/websites/death-knight

docker build -t death-knight-service .

docker rm -f death-knight-service || true

docker run --name death-knight-service -d -p 8080:8081 death-knight-service

exit

eeooff

echo done!