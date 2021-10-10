#agent="/opt/newrelic/newrelic.jar"
agent=/Users/tcrone/lib/nrjars/newrelic-agent-7.3.0.jar
config="/opt/newrelic/newrelic.yml"
#config=/Users/tcrone/conf/disable-mysql.yml
env="staging"
jar="build/libs/mysql-demo-0.0.1-SNAPSHOT.jar"
./gradlew build
java -Dnewrelic.config.file=$config -Dnewrelic.environment=$env -javaagent:$agent -jar $jar
