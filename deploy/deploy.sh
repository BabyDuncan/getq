#!/bin/bash
cd getq/
mvn clean install
cd target/
java -cp ./getq-1.0-SNAPSHOT.jar com.babyduncan.getq.GetQQ
tail -100f /f/opt/logs/stdout-getqq.log
