package uk.dansiviter.microservices.vertx;

import static io.reactiverse.junit5.web.TestRequest.bodyResponse;
import static io.reactiverse.junit5.web.TestRequest.statusCode;
import static io.reactiverse.junit5.web.TestRequest.testRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.reactiverse.junit5.web.WebClientOptionsInject;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
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
		var fileStore = new ConfigStoreOptions()
		.setType("file")
		.setConfig(new JsonObject().put("path", "config.json"));

		var retriever = ConfigRetriever.create(vertx, new ConfigRetrieverOptions().addStore(fileStore));
		retriever.getConfig(c -> {
			vertx.deployVerticle(new Server(), new DeploymentOptions().setConfig(c.result()), testContext.succeedingThenComplete());
		});
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

	@Test
	void people(WebClient client, VertxTestContext testContext) {
		testRequest(client, HttpMethod.GET, "/people/Lois") // Build the request
			.expect(
				statusCode(200),
				bodyResponse(Buffer.buffer("{\"name\":\"Lois\",\"age\":41}"), "application/json")
			)
			.send(testContext);
	}
}
