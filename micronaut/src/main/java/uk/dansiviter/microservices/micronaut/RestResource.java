package uk.dansiviter.microservices.micronaut;

import java.util.Optional;

import org.reactivestreams.Publisher;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
import reactor.core.publisher.Mono;
import uk.dansiviter.microservices.CustomException;
import uk.dansiviter.microservices.Person;
import uk.dansiviter.microservices.ResponseUtil;

@Controller
public class RestResource {
	@Inject
	private PersonRepo repo;

	@Get(uri = "/hello/{name}", produces = MediaType.TEXT_PLAIN)
	public Publisher<String> hello(String name) {
		return Mono.just(ResponseUtil.create(name));
	}

	@Get(uri = "/hello/error", produces = MediaType.TEXT_PLAIN)
	public Publisher<String> error() {
		throw new CustomException();
	}

	@Get(uri = "/people/{name}", produces = MediaType.APPLICATION_JSON)
	public Optional<Person> person(String name) {
		return repo.findById(name);
	}
}
