#!/bin/bash
echo "Killing the app running on 8081"
PID=$(sudo lsof -t -i:8081)
if [ $PID ]; then
	kill -9 $(sudo lsof -t -i:8081)
fi
exit 0