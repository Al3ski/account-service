#!/usr/bin/env bash
set -e
mainClass="AccountServiceApplication"

PID=$(ps -ef | grep "$mainClass" | grep -v grep | awk '{ print $2 }')
if [ -z "$PID" ]; then
    printf '\nApplication is already stopped\n\n'
else
    printf "\nStopping account-service application with PID: %s...\n\n" "$PID"
    kill "$PID"
    printf "Successfully stopped!\n\n"
fi