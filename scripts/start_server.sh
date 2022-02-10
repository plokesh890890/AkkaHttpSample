#!/bin/bash
echo "Starting server..."
cd /home/ec2-user/app1/scripts
nohup java -jar ../AkkatHttpTest-1.0-SNAPSHOT.jar
echo "DEBUG: after java command"
exit 0