#!/bin/bash
echo "Starting server..."
cd "$(dirname "${BASH_SOURCE[0]}")"
pwd
cd /home/ec2-user/app1/scripts
pwd
ls
java -jar ../AkkatHttpTest-1.0-SNAPSHOT.jar