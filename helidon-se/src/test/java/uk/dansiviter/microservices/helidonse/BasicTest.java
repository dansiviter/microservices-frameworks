package uk.dansiviter.microservices.helidonse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.helidon.webclient.WebClient;
import io.helidon.webserver.WebServer;

class BasicTest {
	private static WebServer webServer;
	private static WebClient webClient;

	@BeforeAll
	static void startTheServer() throws Exception {
		webServer = Main.startServer(0).await();
		webClient = WebClient.builder().baseUri("http://localhost:" + webServer.port()).build();
	}

	@Test
	void hello() throws Exception {
		var actual = webClient.get().path("/hello/foo").request().await();
		assertThat(actual.status().code(), is(200));
		assertThat(actual.content().as(String.class).await(), is("Hello foo!"));
	}

	@Test
	void error() throws Exception {
		var actual = webClient.get().path("/hello/error").request().await();
		assertThat(actual.status().code(), is(400));
		assertThat(actual.content().as(String.class).await(), is("Oh no!"));
	}

	@AfterAll
	static void stopServer() throws Exception {
		if (webServer != null) {
			webServer.shutdown().toCompletableFuture().get(10, TimeUnit.SECONDS);
		}
	}
}
