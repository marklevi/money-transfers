#!/usr/bin/env bash

./gradlew shadowJar

java -jar build/libs/money-transfers-1.0-SNAPSHOT-all.jar server money-transfers.yml


