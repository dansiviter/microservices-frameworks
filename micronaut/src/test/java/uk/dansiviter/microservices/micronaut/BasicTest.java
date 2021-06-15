package uk.dansiviter.microservices.micronaut;



import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.inject.Inject;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;

@MicronautTest
public class BasicTest {

	@Inject
	@Client("/")
	RxHttpClient client;

	@Test
	void hello() {
		var actual = client.toBlocking().exchange(HttpRequest.GET("/hello/foo"), String.class);
		assertThat(actual.status().getCode(), is(200));
		assertThat(actual.body(), is("Hello foo!"));
	}

	@Test
	void error() {
		var ex = assertThrows(HttpClientResponseException.class,
			() -> client.toBlocking().exchange(HttpRequest.GET("/hello/error")));

		var actual = ex.getResponse();
		assertThat(actual.status().getCode(), is(400));
		assertThat(actual.getBody(String.class).get(), is("Oh no!"));
	}

	@Test
	@Disabled("No idea why but this hangs in test!")
	void people() {
		var actual = client.toBlocking().exchange(HttpRequest.GET("/people/Lois"), String.class);
		assertThat(actual.status().getCode(), is(200));
		assertThat(actual.body(), is("{\"age\":41,\"name\":\"Lois\"}"));
	}
}
