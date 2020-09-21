#!/bin/bash
PROFILE='prod'
TARGET_URL='http://127.0.0.1'
TARGET_PORT=8181

ORIGIN_JAR_PATH='/home/github-actions/dev-timeline-batch/deploy/*.jar'
ORIGIN_PATH='/home/github-actions/dev-timeline-batch'
INC_PATH='/home/github-actions/dev_timeline_batch_url.inc'

TARGET_PID=$(lsof -Fp -i TCP:${TARGET_PORT} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')

if [ ! -z ${TARGET_PID} ]; then
  echo "> Kill WAS running at ${TARGET_PORT}."
  sudo kill ${TARGET_PID}
fi

nohup java -jar -Dserver.port=${TARGET_PORT} -Dspring.profiles.active=${PROFILE} ${ORIGIN_JAR_PATH} > ${ORIGIN_PATH}/nohup.out 2>&1 &
echo "> Now new WAS runs at ${TARGET_PORT}."
exit 0