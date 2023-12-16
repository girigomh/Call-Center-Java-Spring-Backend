#!/bin/bash
#Package the application in Production or Local by using (prod or local)
mvn clean
mvn package -P dev -Dmaven.test.skip=true