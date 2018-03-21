#!/usr/bin/env bash

mvn clean install -pl content && mvn -P start exec:exec@content -DrunAsync=false
