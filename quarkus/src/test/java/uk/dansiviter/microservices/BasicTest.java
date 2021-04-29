package uk.dansiviter.microservices;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@TestHTTPEndpoint(RestResource.class)
public class BasicTest {
	@Test
	public void testHelloEndpoint() {
		given().when().get("/foo").then().statusCode(200).body(is("Hello foo!"));
	}
}
