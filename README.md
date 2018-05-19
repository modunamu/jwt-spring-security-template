# JWT Spring Security Rest Template
![Screenshot from running application on Swagger](etc/swagger.png?raw=true "Screenshot Swagger")


## About
This is a small framework for using **JWT (JSON Web Token)** with **Spring Boot 2**, **Swagger** and 
**Spring Security**. This solution is partially based on the demo application [JWT Spring Security Demo](https://github.com/szerhusenBC/jwt-spring-security-demo) and refered below `Resources`. Thanks to the author!


## Requirements

* Java 1.8
* Maven >= 3.2.1
* Spring Boot 2.0.1
* Lombok 1.16.20
* H2 Database
* Swagger 2.8.0


## Run
```bash
mvn spring-boot:run
```

## Swagger
You can visit the URL in your browser: 
```
http://localhost:8080/swagger-ui.html
```

## H2 Database
```
http://localhost:8080/h2-console
```

## Usage

### Login
To login, add below code to the body of a POST request at '/api/auth':
`{"username":"admin","password":"admin"}`

```
curl -i -H "Content-Type: application/json" -X POST -d '{"username":"admin","password":"admin"}' http://localhost:8080/api/auth
HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Fri, 11 May 2018 12:07:32 GMT

{
  "token" : "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTUyNjY0NTI1MiwiaWF0IjoxNTI2MDQwNDUyfQ.y9QLvx3se9YgOIh-qZF3HMXEu22fl4fTaszZ_ZR3kLCTZPfPksm_s85uP6ei7ChW8jYK0jrRHRD1EtdzjVnQdw"
}
```

To access the 
authenticated resources '/api/v1/user', add to the `Authentication: Bearer` header of the request the token obtained by logging in:

`Authentication: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTUyNjY0NTI1MiwiaWF0IjoxNTI2MDQwNDUyfQ.y9QLvx3se9YgOIh-qZF3HMXEu22fl4fTaszZ_ZR3kLCTZPfPksm_s85uP6ei7ChW8jYK0jrRHRD1EtdzjVnQdw`

```
curl -i -X GET -H "Content-Type: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTUyNzEzOTg4MSwiaWF0IjoxNTI2NTM1MDgxfQ.EfDJ6AgUG3TCKsMqUzqAbjeCoRthGb0s-PEsYNKkiIeyHh9yKM6gBQDdfPHo2Cz5pQQuCqFiwzr2Ds-w2V7XYg" "http://localhost:8080/api/v1/user"
```

### Sign up
To sign up, refer to the following for registration.
```
curl -i -X POST -H "Content-Type: application/json" -d '{
  "username" : "admin2",
  "password" : "admin2",
  "firstname" : "admin2",
  "lastname" : "admin2",
  "email" : "admin2@admin2.com"
}' "http://localhost:8080/api/auth/signup"
```


## Resources

This demo has been inspired by the following articles:

* [JWT Spring Security Demo](https://github.com/szerhusenBC/jwt-spring-security-demo)

* [Custom Error Message Handling for REST API](http://www.baeldung.com/global-error-handler-in-a-spring-rest-api)

* [Spring Boot REST Application + Spring Security with JWT](https://github.com/ThomasVitale/spring-security-jwt-rest-demo)

* [Setting Up Swagger 2 with a Spring REST API](http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api)


## Copyright and license

The code is released under the [MIT license](LICENSE?raw=true).
