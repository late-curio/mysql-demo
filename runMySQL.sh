docker run --name test-mysql-$1 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=spring_app_db -d mysql:$1
