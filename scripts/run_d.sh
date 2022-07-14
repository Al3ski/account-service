#!/usr/bin/env bash
set -e
currentDir=$(cd -P -- "$(dirname -- "$0")" && pwd -P)
rootDir="$currentDir/../"

imageTag=$1
if [ -z "$1" ]
  then
    printf "\nNo image tag provided. Latest will be used instead.\n\n"
    imageTag=latest
fi

imageName="finance/account-app:$imageTag"
host="localhost"
port=8091
adminPort=9091
appUrl="http://$host:$port/v1/accounts/details"
swaggerUrl="http://$host:$adminPort/actuator/swagger-ui/index.html#/"

printf "Starting account-service...\n\n"
printf "[Step 1] Removing old images with name: %s...\n" "$imageName"
(docker rmi -f "$imageName")
printf "\n[Step 2] Building new image: %s...\n" "$imageName"
(docker build -t "$imageName" "$rootDir")
printf "\n[Step 3] Starting new container...\n"
(docker run -d -p "$port":"$port" -p "$adminPort":"$adminPort" "$imageName")

printf "\n[Step 4] Starting application...\n"
while ! curl -sf "$appUrl" 1>/dev/null 2>&1; do
  printf "··"
  ((count++)) && ((count == 30)) && break
  sleep 1
done

printf "\n\nSuccessfully started!\n\n"

{
  xdg-open "$swaggerUrl" || sensible-browser "$swaggerUrl" || x-www-browser "$swaggerUrl" \
  || gnome-open "$swaggerUrl" || open -u "$swaggerUrl"
} 2>/dev/null || printf "To see available Endpoints description, follow the link:\n %s\n\n" "$swaggerUrl"