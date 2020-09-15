#!/bin/bash

# Crawl current connected port of WAS
TARGET_URL='http://127.0.0.1'
A_PORT=8081
B_PORT=8082

INC_PATH='/home/github-actions/dev_timeline_api_url.inc'

CURRENT_PORT=$(cat ${INC_PATH} | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0

# Toggle port Number
if [ ${CURRENT_PORT} -eq ${A_PORT} ]; then
    TARGET_PORT=${B_PORT}
elif [ ${CURRENT_PORT} -eq ${B_PORT} ]; then
    TARGET_PORT=${A_PORT}
else
    echo "> No WAS is connected to nginx"
    exit 1
fi


echo "> Start health check of WAS at '${TARGET_URL}:${TARGET_PORT}' ..."

for RETRY_COUNT in 1 2 3 4 5 6 7 8 9 10
do
    echo "> #${RETRY_COUNT} trying..."
    RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}"  ${TARGET_URL}:${TARGET_PORT}/health)

    if [ ${RESPONSE_CODE} -eq 200 ]; then
        echo "> New WAS successfully running"
        exit 0
    elif [ ${RETRY_COUNT} -eq 10 ]; then
        echo "> Health check failed."
        exit 1
    fi
    sleep 10
done