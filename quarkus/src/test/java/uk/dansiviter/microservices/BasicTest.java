package uk.dansiviter.microservices;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;

@QuarkusTest
public class BasicTest {
	@TestHTTPEndpoint(RestResource.class)
	@TestHTTPResource
	URL url;

	@BeforeEach
	public void beforeAll() {
		RestAssured.port = url.getPort();
	}

	@Test
	public void testHelloEndpoint() {
		given().when().get("/hello/foo").then().statusCode(200).body(is("Hello foo!"));
	}
}
