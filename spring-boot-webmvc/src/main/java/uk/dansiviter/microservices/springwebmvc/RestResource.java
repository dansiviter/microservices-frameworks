package uk.dansiviter.microservices.springwebmvc;

import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import uk.dansiviter.microservices.CustomException;
import uk.dansiviter.microservices.ResponseUtil;

@RestController
public class RestResource {
	@GetMapping(path = "/hello/{name}", produces = TEXT_PLAIN_VALUE)
	public String hello(@PathVariable("name") String name) {
		return ResponseUtil.create(name);
	}

	@GetMapping(path = "/hello/error", produces = TEXT_PLAIN_VALUE)
	public String error() {
		throw new CustomException();
	}

	@ExceptionHandler(CustomException.class)
	ResponseEntity<String> customExceptionHandler(CustomException ex) {
		return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(ex.getMessage());
	}
}
