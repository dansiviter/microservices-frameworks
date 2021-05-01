package uk.dansiviter.microservices.dropwizard;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;

@ExtendWith(DropwizardExtensionsSupport.class)
class BasicTest {
	private static final ResourceExtension EXT = ResourceExtension.builder()
		.addResource(RestResource.class)
		.addProvider(CustomExceptionHandler.class)
		.build();

	@BeforeAll
	static void startTheServer() throws Exception {
	}

	@Test
	void hello() throws Exception {
		var actual = EXT.target("/hello/foo").request().get();
		assertThat(actual.getStatus(), is(200));
		assertThat(actual.readEntity(String.class), is("Hello foo!"));
	}

	@Test
	void error() throws Exception {
		var actual = EXT.target("/hello/error").request().get();
		assertThat(actual.getStatus(), is(400));
		assertThat(actual.readEntity(String.class), is("Oh no!"));
	}
}
