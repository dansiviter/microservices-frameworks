package uk.dansiviter.microservices.springwebflux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
import uk.dansiviter.microservices.CustomException;
import uk.dansiviter.microservices.ResponseUtil;

@RestController
public class HelloController {
	@GetMapping(path = "/hello/error")
	public Mono<Void> error() {
		throw new CustomException();
	}

	@GetMapping(path = "/hello/{name}")
	public Mono<String> hello(@PathVariable("name") String name) {
		return Mono.just(ResponseUtil.create(name));
	}
}
