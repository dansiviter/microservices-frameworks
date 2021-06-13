package uk.dansiviter.microservices.helidonmp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import org.junit.jupiter.api.Test;

import io.helidon.microprofile.tests.junit5.HelidonTest;

@HelidonTest
class BasicTest {
	@Inject
	WebTarget webTarget;

	@Test
	void hello() {
		var actual = webTarget.path("/hello/foo").request().get();
		assertThat(actual.getStatus(), is(200));
		assertThat(actual.readEntity(String.class), is("Hello foo!"));
	}

	@Test
	void error() throws Exception {
		var actual = webTarget.path("/hello/error").request().get();
		assertThat(actual.getStatus(), is(400));
		assertThat(actual.readEntity(String.class), is("Oh no!"));
	}

	@Test
	void people() throws Exception {
		var actual = webTarget.path("/people/Lois").request().get();
		assertThat(actual.getStatus(), is(200));
		assertThat(actual.readEntity(String.class), is("{\"age\":41,\"name\":\"Lois\"}"));
	}
}
