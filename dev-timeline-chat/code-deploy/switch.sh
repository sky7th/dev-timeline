#!/bin/bash

# Crawl current connected port of WAS
TARGET_URL='http://127.0.0.1'
A_PORT=8281
B_PORT=8282

ORIGIN_JAR_PATH='/home/github-actions/dev-timeline-chat/deploy/*.jar'
INC_NAME='dev_timeline_chat_url'
INC_PATH='/home/github-actions/dev_timeline_chat_url.inc'

CURRENT_PORT=$(cat ${INC_PATH}  | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0

echo "> Nginx currently proxies to ${CURRENT_PORT}."

# Toggle port number
if [ ${CURRENT_PORT} -eq ${A_PORT} ]; then
    TARGET_PORT=${B_PORT}
elif [ ${CURRENT_PORT} -eq ${B_PORT} ]; then
    TARGET_PORT=${A_PORT}
else
    echo "> No WAS is connected to nginx"
    exit 1
fi

# Change proxying port into target port
echo "set \$${INC_NAME} ${TARGET_URL}:${TARGET_PORT};" | tee ${INC_PATH}

echo "> Now Nginx proxies to ${TARGET_PORT}."

# Reload nginx
sudo service nginx reload
echo "> Nginx reloaded."

sudo fuser -k -n tcp ${CURRENT_PORT}
echo "> Current Port Killed."