package uk.dansiviter.microservices.vertx;

import java.lang.management.ManagementFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import uk.dansiviter.microservices.CustomException;
import uk.dansiviter.microservices.ResponseUtil;

public class Server extends AbstractVerticle {
  @Override
  public void start() throws Exception {
		var router = Router.router(vertx);
		router.route(HttpMethod.GET, "/hello/error").handler(this::error).failureHandler(this::errorHandler);
		router.route(HttpMethod.GET, "/hello/:name").handler(this::hello);
		router.errorHandler(500, this::errorHandler);
		vertx.createHttpServer()
			.requestHandler(router)
			.listen(8080)
			.onComplete(r -> {
				var initializationElapsedTime = ManagementFactory.getRuntimeMXBean().getUptime();
				System.out.printf("Server started on http://localhost:%d in %d milliseconds (since JVM startup).%n", r.result().actualPort(), initializationElapsedTime);
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

	private void errorHandler(RoutingContext ctx) {
		if (ctx.failure() instanceof CustomException) {
			ctx.response().setStatusCode(400).end(ctx.failure().getMessage());
		} else {
			ctx.next();
		}
	}
}
