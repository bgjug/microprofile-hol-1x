#!/usr/bin/env bash

mvn clean install -pl gui && gui/target/apache-tomee/bin/catalina.sh jpda run
