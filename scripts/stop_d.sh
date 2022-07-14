#!/usr/bin/env bash
set -e
imageNameBase="finance/account-app*"

CID="$(docker container ps | grep "$imageNameBase" | awk '{print $1}')"
if [ -z "$CID" ]; then
    printf '\nApplication is already stopped\n\n'
else
    printf "\nStopping account-service application with CID: %s...\n\n" "$CID"
    (docker container stop "$CID")
    printf "\nSuccessfully stopped!\n\n"
fi