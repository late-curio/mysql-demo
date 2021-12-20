# SQL Demo

## Start MySQL8 from Docker

`./runMySQL.sh`

## Build and Run Demo application with New Relic Agent

`./runWithAgent.sh`

OR

`./gradlew bootRun`

## Run Test Endpoints

URI: ``

`POST` endpoints require content `payload` and return the `id` to be used in `GET` to fetch `content`

### POST Content via Spring JPA Repository

`POST http://localhost:8080/content/jpa-repository`

### POST Content via Spring JDBC Prepared Statement

`POST http://localhost:8080/content/jdbc-prepared-statement`

### POST Content via Spring JDBC Statement 
(Manually creates SQL with String concatenation and escapes single quotes manually)

`POST http://localhost:8080/content/jdbc-prepared-statement`

### GET Content

`GET http://localhost:8080/content/{id}`

Returns text from original content that was posted for given `id`