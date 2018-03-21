mvn clean install -pl subscribers && mvn -P start exec:exec@subscribers -DrunAsync=false
