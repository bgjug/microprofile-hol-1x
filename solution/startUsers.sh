#!/usr/bin/env bash

mvn clean install -pl users && mvn -P start exec:exec@users -DrunAsync=false
