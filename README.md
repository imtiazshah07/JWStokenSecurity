# JWS (JSON Web Token)

The application is build with following API's:

- [Java] (https://www.oracle.com/java/technologies/javase-jdk8-downloads.html)
- [Spring Boot] (https://spring.io/projects/spring-boot/)
- [Spring JPA] (https://spring.io/guides/gs/accessing-data-jpa/)
- [Jackson] (https://github.com/FasterXML/jackson-databind/wiki)
- [Swagger] (https://swagger.io/)
- [IO.JsonWebToken] (https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt)
- [PostGresSQL] (https://www.postgresql.org/)

## Getting Started 

### Registration 
API to register the users.

- Payload 
    ``` {"name":"Syed Imtiaz","username":"isyed","password":"myPassword"} ``` 

Method	| Path	| Description	
------------- | ------------------------- | ------------- 
POST	| /register | API to register the user.

### Authentication 
API for authentication 

- Payload 
    ``` {"username":"isyed","password":"myPassword"} ``` 

Method	| Path	| Description	
------------- | ------------------------- | ------------- 
POST	| /authenticate | API to authentication.

### List Of Users
API to list down all the users.

Method	| Path	| Description	
------------- | ------------------------- | ------------- 
POST	| /listUsers | API to authentication.

### Maven Build 

``` java
$mvn test
$mvn clean install -U
$java -jar target/JWStokenSecurity-0.0.1-SNAPSHOT.jar
```
