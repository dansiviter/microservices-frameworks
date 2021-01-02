package uk.dansiviter.microservices.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import uk.dansiviter.microservices.ResponseUtil;

@Configuration
public class Config {
	@Bean
	public RouterFunction<ServerResponse> route() {
		return RouterFunctions.route(
				RequestPredicates.GET("/hello/{name}").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
				this::hello);
	}

	public Mono<ServerResponse> hello(ServerRequest request) {
		return ServerResponse.ok()
				.contentType(MediaType.TEXT_PLAIN)
				.body(BodyInserters.fromValue(ResponseUtil.create(request.pathVariable("name"))));
	}
}
