#!/bin/bash
cd getq/
mvn clean install
cd target/
nohup java -cp ./getq-1.0-SNAPSHOT.jar com.babyduncan.getq.GetQQ > /etc/null 2>&1 &
tail -100f /f/opt/logs/stdout-getqq.log
