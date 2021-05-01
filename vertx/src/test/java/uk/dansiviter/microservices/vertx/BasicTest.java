package uk.dansiviter.microservices.vertx;

import static io.reactiverse.junit5.web.TestRequest.bodyResponse;
import static io.reactiverse.junit5.web.TestRequest.statusCode;
import static io.reactiverse.junit5.web.TestRequest.testRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.reactiverse.junit5.web.WebClientOptionsInject;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

@ExtendWith(VertxExtension.class)
class BasicTest {

	@WebClientOptionsInject
  public WebClientOptions options = new WebClientOptions()
    .setDefaultHost("localhost")
    .setDefaultPort(8080);

	@BeforeEach
	void before(Vertx vertx, VertxTestContext testContext) {
    vertx.deployVerticle(new Server(), testContext.succeedingThenComplete());
 	}

	@Test
  void hello(WebClient client, VertxTestContext testContext) {
		testRequest(client, HttpMethod.GET, "/hello/foo") // Build the request
      .expect(
				statusCode(200),
				bodyResponse(Buffer.buffer("Hello foo!"), "text/plain")
      )
      .send(testContext);
	}

	@Test
  void error(WebClient client, VertxTestContext testContext) {
		testRequest(client, HttpMethod.GET, "/hello/error") // Build the request
      .expect(
		    statusCode(400),
    		bodyResponse(Buffer.buffer("Oh no!"), null)
      )
      .send(testContext);
	}
}
