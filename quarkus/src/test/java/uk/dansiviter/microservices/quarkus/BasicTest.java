package uk.dansiviter.microservices.quarkus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class BasicTest {
	@Test
	void hello() {
		given().when().get("hello/foo").then().statusCode(200).body(is("Hello foo!"));
	}

	@Test
	void error() {
		given().when().get("hello/error").then().statusCode(400).body(is("Oh no!"));
	}

	@Test
	void person() {
		given().when().get("people/Lois").then().statusCode(200).body(is("{\"age\":41,\"name\":\"Lois\"}"));
	}
}
