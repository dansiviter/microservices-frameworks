package uk.dansiviter.microservices.micronaut;

import java.util.Optional;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.reactivex.Single;
import jakarta.inject.Inject;
import uk.dansiviter.microservices.CustomException;
import uk.dansiviter.microservices.Person;
import uk.dansiviter.microservices.ResponseUtil;

@Controller
public class RestResource {
	@Inject
	private PersonRepo repo;

	@Get(uri = "/hello/{name}", produces = MediaType.TEXT_PLAIN)
	public Single<String> hello(String name) {
		return Single.just(ResponseUtil.create(name));
	}

	@Get(uri = "/hello/error", produces = MediaType.TEXT_PLAIN)
	public Single<String> error() {
		throw new CustomException();
	}

	@Get(uri = "/people/{name}", produces = MediaType.APPLICATION_JSON)
	public Optional<Person> person(String name) {
		return repo.findById(name);
	}
}
