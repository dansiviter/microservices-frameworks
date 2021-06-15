package uk.dansiviter.microservices.dropwizard;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import javax.ws.rs.client.WebTarget;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;

@ExtendWith(DropwizardExtensionsSupport.class)
class BasicTest {

	private static DropwizardAppExtension<BaseConfig> EXT = new DropwizardAppExtension<>(
		Main.class,
		"config.yaml");

	private WebTarget target() {
		return EXT.client().target(String.format("http://localhost:%d/", EXT.getLocalPort()));
	}

	@Test
	void hello() throws Exception {
		var actual = target().path("/hello/foo").request().get();
		assertThat(actual.getStatus(), is(200));
		assertThat(actual.readEntity(String.class), is("Hello foo!"));
	}

	@Test
	void error() throws Exception {
		var actual =target().path("/hello/error").request().get();
		assertThat(actual.getStatus(), is(400));
		assertThat(actual.readEntity(String.class), is("Oh no!"));
	}

	@Test
	void people() throws Exception {
		var actual = target().path("/people/Lois").request().get();
		assertThat(actual.getStatus(), is(200));
		assertThat(actual.readEntity(String.class), is("{\"name\":\"Lois\",\"age\":41}"));
	}
}
