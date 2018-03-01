#!/usr/bin/env bash

mvn clean install -pl gui && mvn -P start exec:exec@gui
