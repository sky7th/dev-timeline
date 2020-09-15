#!/bin/bash
PROFILE='prod'
TARGET_URL='http://127.0.0.1'
A_PORT=8081
B_PORT=8082

ORIGIN_JAR_PATH='/home/github-actions/dev-timeline-api/deploy/*.jar'
ORIGIN_PATH='/home/github-actions/dev-timeline-api'
INC_PATH='/home/github-actions/dev_timeline_api_url.inc'



CURRENT_PORT=$(cat ${INC_PATH} | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0

echo "> Current port of running WAS is ${CURRENT_PORT}."

if [ ${CURRENT_PORT} -eq ${A_PORT} ]; then
  TARGET_PORT=${B_PORT}
elif [ ${CURRENT_PORT} -eq ${B_PORT} ]; then
  TARGET_PORT=${A_PORT}
else
  echo "> No WAS is connected to nginx"
fi

TARGET_PID=$(lsof -Fp -i TCP:${TARGET_PORT} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')

if [ ! -z ${TARGET_PID} ]; then
  echo "> Kill WAS running at ${TARGET_PORT}."
  sudo kill ${TARGET_PID}
fi

nohup java -jar -Dserver.port=${TARGET_PORT} -Dspring.profiles.active=${PROFILE} ${ORIGIN_JAR_PATH} > ${ORIGIN_PATH}/nohup.out 2>&1 &
echo "> Now new WAS runs at ${TARGET_PORT}."
exit 0