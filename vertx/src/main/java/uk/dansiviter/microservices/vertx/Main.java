package uk.dansiviter.microservices.vertx;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class Main {
	public static void main(String... args) {
		var fileStore = new ConfigStoreOptions()
			.setType("file")
			.setConfig(new JsonObject().put("path", "config.json"));

		var vertx = Vertx.vertx();
		var retriever = ConfigRetriever.create(vertx, new ConfigRetrieverOptions().addStore(fileStore));
		retriever.getConfig(c -> {
			vertx.deployVerticle(new Server(), new DeploymentOptions().setConfig(c.result()));
		});
	}
}
