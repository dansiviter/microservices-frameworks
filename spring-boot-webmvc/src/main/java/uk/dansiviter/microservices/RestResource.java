package uk.dansiviter.microservices;

import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestResource {
	@GetMapping(path = "/hello/{name}", produces = TEXT_PLAIN_VALUE)
	public String hello(@PathVariable("name") String name) {
        return ResponseUtil.create(name);
    }
}
