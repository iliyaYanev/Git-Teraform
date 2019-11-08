#!/bin/bash
SERVICE_NAME="demo"
IMAGE_VERSION="latest"
TASK_FAMILY="demo"

# Update the service with the new task definition and desired count
TASK_REVISION=`aws ecs describe-task-definition --task-definition demo | egrep "revision" | tr "/" " " | awk '{print $2}' | sed 's/"$//'`
DESIRED_COUNT=`aws ecs describe-services --services ${SERVICE_NAME} | egrep "desiredCount" | tr "/" " " | awk '{print $2}' | sed 's/,$//'`
if [ ${DESIRED_COUNT} = "0" ]; then
    DESIRED_COUNT="1"
fi

aws ecs update-service --cluster default --service ${SERVICE_NAME} --task-definition ${TASK_FAMILY}:${TASK_REVISION} --desired-count ${DESIRED_COUNT}