jar="build/libs/mysql-demo-0.0.1-SNAPSHOT.jar"
./gradlew build
java -Dnewrelic.config.file=$NRCONFIG -Dnewrelic.environment=$NRENV -javaagent:$NRAGENT -jar $jar
