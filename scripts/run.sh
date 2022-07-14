#!/usr/bin/env bash
set -e
currentDir=$(cd -P -- "$(dirname -- "$0")" && pwd -P)
rootDir="$currentDir/../"

port=8091
adminPort=9091
host="localhost"
appUrl="http://$host:$port/v1/accounts/details"
swaggerUrl="http://$host:$adminPort/actuator/swagger-ui/index.html#/"

printf "\nStarting account-service...\n"
printf "\n[Step 1] Compiling application...\n"
(cd "$rootDir" && exec ./mvnw clean install)
printf "\n[Step 2] Starting application...\n"
(cd "$rootDir" && exec nohup ./mvnw spring-boot:run -rf :app &)

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