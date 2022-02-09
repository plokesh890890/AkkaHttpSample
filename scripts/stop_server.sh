#!/bin/bash
echo "Killing the app running on 8081"
kill -9 $(sudo lsof -t -i:8081)