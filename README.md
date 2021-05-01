![GitHub Workflow Status](https://img.shields.io/github/workflow/status/dansiviter/microservices-frameworks/Java%20CI?style=flat-square)

# Microservices Frameworks #

This project demonstrates some very simple microservices that conform to the same specification:
* [Helidon SE - Reactive server](./helidon-se/)
* [Helidon MP - Microprofile](./helidon-mp/)
* [Micronaut](./micronaut/)
* [Quarkus](./quarkus/)
* [Spring Boot WebFlux](./spring-boot-webflux/)
* [Spring Boot WebMVC](./spring-boot-webmvc/)
* [Vert.x](./vertx)

Config:
* Port: `8080`
* URIs:
  * `GET /hello/{name}`: Respond with `Hello {name}!`.
  * `GET /hello/error`: Throw a `CustomException` which should map to a `400 Bad Request`.
