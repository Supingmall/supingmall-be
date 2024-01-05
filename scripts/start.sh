#!/bin/bash

# .code.env 파일 읽기
if [ -f ~/code.env ]; then
  echo "File exists: ~/code.env"
  source ~/code.env
  echo "File sourced successfully."
else
  echo "File not found: ~/code.env"
fi


# .code.env 파일 읽기
if [ -f ~/code.env ]; then
  export $(cat ~/code.env | xargs)
fi

PROJECT_ROOT="/home/ubuntu/sihu/springBest"
JAR_FILE="/home/ubuntu/sihu/springBest/susu/spring.jar"

APP_LOG="$PROJECT_ROOT/application.log"
ERROR_LOG="$PROJECT_ROOT/error.log"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"
# 직접 환경 변수 설정
#export DATABASE_USERNAME="root"
#export DATABASE_PASSWORD="12341234"
#export JWT_CODE="cheerupBE2"


TIME_NOW=$(date +%c)

echo "$TIME_NOW > $JAR_FILE 파일 복사" >> $DEPLOY_LOG
cp $PROJECT_ROOT/susu/*.jar $JAR_FILE

#codedeploy bashrc를 읽어오지 못해 해당 파일 로드하게 작업
#source ~/.bash_profile

echo "$TIME_NOW > $JAR_FILE 파일 실행" >> $DEPLOY_LOG
nohup java -jar $JAR_FILE > $APP_LOG 2> $ERROR_LOG &

CURRENT_PID=$(pgrep -f $JAR_FILE)
echo "$TIME_NOW > 실행된 프로세스 아이디 $CURRENT_PID 입니다." >> $DEPLOY_LOG