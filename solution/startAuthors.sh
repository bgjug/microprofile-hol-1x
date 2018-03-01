#!/usr/bin/env bash

mvn clean install -pl authors && mvn -P start exec:exec@authors -DrunAsync=false
