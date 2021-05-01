package uk.dansiviter.microservices.springwebmvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class BaseTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void hello() {
		var actual = this.restTemplate.getForEntity("http://localhost:" + port + "/hello/foo", String.class);
		assertThat(actual.getStatusCode().value(), is(200));
		assertThat(actual.getBody(), is("Hello foo!"));
	}

	@Test
	void error() {
		var actual = this.restTemplate.getForEntity("http://localhost:" + port + "/hello/error", String.class);
		assertThat(actual.getStatusCode().value(), is(400));
		assertThat(actual.getBody(), is("Oh no!"));
	}
}
