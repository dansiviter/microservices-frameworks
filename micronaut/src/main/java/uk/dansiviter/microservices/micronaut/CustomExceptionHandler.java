package uk.dansiviter.microservices.micronaut;

import javax.inject.Singleton;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import uk.dansiviter.microservices.CustomException;

@Produces
@Singleton
@Requires(classes = { CustomException.class, ExceptionHandler.class })
public class CustomExceptionHandler implements ExceptionHandler<CustomException, HttpResponse<String>> {
    @Override
		@SuppressWarnings("rawtypes")
    public HttpResponse<String> handle(HttpRequest request, CustomException ex) {
        return HttpResponse.badRequest(ex.getMessage());
    }
}
