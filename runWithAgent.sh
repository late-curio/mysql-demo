agent="/opt/newrelic/newrelic.jar"
config="/opt/newrelic/newrelic.yml"
env="staging"
jar="build/libs/mysql-demo-0.0.1-SNAPSHOT.jar"
./gradlew build
java -Dnewrelic.config.file=$config -Dnewrelic.environment=$env -javaagent:$agent -jar $jar
