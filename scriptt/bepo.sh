#!/bin/bash

cd /home/ubuntu/spring/supingmall-be

git pull origin feature-sihu-test

OLD_PID=$(pgrep -f "java -jar ./build/libs/shopping-mall-project-0.0.1-SNAPSHOT.jar")
if [ -n "$OLD_PID" ]; then
    echo "서버 중지중 PID $OLD_PID..."
    kill -15 "$OLD_PID"
    sleep 5  
fi
echo "빌드시작"
./gradlew bootJar
echo "빌드완료"

nohup java -jar ./build/libs/shopping-mall-project-0.0.1-SNAPSHOT.jar >> /dev/null 2>&1 &
echo "서버 시작"
echo "재배포 완료."

