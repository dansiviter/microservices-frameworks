# Microservices Frameworks #

This project demonstrates some very simple microservices that conform to the same specification:

* Helidon SE
* Helidon MP - Microprofile
* Micronaut
* Quarkus - Microprofile
* Spring Boot WebFlux

* Port: 8080
* URIs:
  * `GET /hello/{name}`: Respond with `Hello {name}!`.
  * `GET /hello/error`: Throw a `CustomException` which should map to a `400 Bad Request`.
