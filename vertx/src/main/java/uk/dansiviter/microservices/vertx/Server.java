package uk.dansiviter.microservices.vertx;

import java.lang.management.ManagementFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import uk.dansiviter.microservices.CustomException;
import uk.dansiviter.microservices.Person;
import uk.dansiviter.microservices.ResponseUtil;

public class Server extends AbstractVerticle {
  @Override
  public void start() throws Exception {
		var client = JDBCClient.create(vertx, config().getJsonObject("db"));
		var router = Router.router(vertx);
		router.route(HttpMethod.GET, "/hello/error").handler(this::error).failureHandler(this::errorHandler);
		router.route(HttpMethod.GET, "/hello/:name").handler(this::hello);
		router.route(HttpMethod.GET, "/people/:name").handler(c -> people(c, client));
		router.errorHandler(500, this::errorHandler);
		var host = config().getString("host");
		vertx.createHttpServer()
			.requestHandler(router)
			.listen(config().getInteger("port"), host)
			.onComplete(r -> {
				var initializationElapsedTime = ManagementFactory.getRuntimeMXBean().getUptime();
				System.out.printf("Server started on http://%s:%d in %d milliseconds (since JVM startup).%n", host, r.result().actualPort(), initializationElapsedTime);
			});
  }

	private void hello(RoutingContext ctx) {
		ctx.response()
			.putHeader(HttpHeaders.CONTENT_TYPE, "text/plain")
			.end(ResponseUtil.create(ctx.pathParam("name")));
	}

	private void error(RoutingContext ctx) {
		throw new CustomException();
	}

	private void people(RoutingContext ctx, JDBCClient client) {
		var name = ctx.pathParam("name");
		client.getConnection(res -> {
			var connection = res.result();
			connection.querySingleWithParams(
				"SELECT * FROM person WHERE name = ?",
				new JsonArray().add(name),
				res2 -> {
					if (res2.succeeded()) {
						var rs = res2.result();
						var person = new Person(rs.getString(0), rs.getInteger(1));
						ctx.response()
						.putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
						.end(Json.encode(person));
					}
				});
		});
	}

	private void errorHandler(RoutingContext ctx) {
		if (ctx.failure() instanceof CustomException) {
			ctx.response().setStatusCode(400).end(ctx.failure().getMessage());
		} else {
			ctx.response().setStatusCode(500).end(ctx.failure().getMessage());
		}
	}
}
