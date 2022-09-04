#!/usr/bin/env bash
set -eu

echo "Backend url: $BACKEND_URL"

location=$(curl --silent --head https://en.wikipedia.org/wiki/Special:Random | sed -n -e '/^location/p')

url=${location:10}
url="${url//[$'\r\n']}"
message="Read $url"
echo "Todo: $message"

json="{\"text\":\"$message\"}"
echo "Payload: $json"

curl -d "$json" -H "Content-Type: application/json" -X POST "${BACKEND_URL}"