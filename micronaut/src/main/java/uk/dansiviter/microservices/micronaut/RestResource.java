package uk.dansiviter.microservices.micronaut;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.reactivex.Single;
import uk.dansiviter.microservices.ResponseUtil;

/**
 * @author Graeme Rocher
 * @since 1.0
 */
@Controller("/hello")
public class RestResource {
	@Get(uri = "/{name}", produces = MediaType.TEXT_PLAIN)
	public Single<String> hello(String name) {
		return Single.just(ResponseUtil.create(name));
	}
}
