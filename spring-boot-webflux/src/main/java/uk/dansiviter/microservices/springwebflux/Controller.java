package uk.dansiviter.microservices.springwebflux;

import static java.nio.charset.StandardCharsets.UTF_8;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.WebFilter;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uk.dansiviter.microservices.CustomException;
import uk.dansiviter.microservices.ResponseUtil;

@Configuration
public class Controller {
	@Bean
	public RouterFunction<ServerResponse> route() {
		return RouterFunctions
				.route(RequestPredicates.GET("/hello/error").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), this::error)
				.andRoute(RequestPredicates.GET("/hello/{name}").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
						this::hello);
	}

	@Bean
	WebFilter customExceptionHandler() {
		return (exchange, next) -> next.filter(exchange).onErrorResume(CustomException.class, ex -> {
			var response = exchange.getResponse();
			response.setStatusCode(HttpStatus.BAD_REQUEST);
			var buffer = response.bufferFactory().wrap(ex.getMessage().getBytes(UTF_8));
			return response.writeWith(Flux.just(buffer));
		});
	}

	public Mono<ServerResponse> error(ServerRequest request) {
		throw new CustomException();
	}

	public Mono<ServerResponse> hello(ServerRequest request) {
		return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
				.body(BodyInserters.fromValue(ResponseUtil.create(request.pathVariable("name"))));
	}
}
