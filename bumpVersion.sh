#!/usr/bin/env bash

newVersion=$1

mvn versions:set -DnewVersion=${newVersion}
cd solution/authors
mvn versions:set -DnewVersion=${newVersion}
cd ../content
mvn versions:set -DnewVersion=${newVersion}
cd ../gui
mvn versions:set -DnewVersion=${newVersion}
cd ../subscribers
mvn versions:set -DnewVersion=${newVersion}
cd ../users
mvn versions:set -DnewVersion=${newVersion}

