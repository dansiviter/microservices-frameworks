package uk.dansiviter.microservices.quarkus;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import uk.dansiviter.microservices.CustomException;

@Provider
public class CustomExceptionHandler implements ExceptionMapper<CustomException> {
	@Override
	public Response toResponse(CustomException exception) {
		return Response.status(Status.BAD_REQUEST)
			.entity(exception.getMessage())
			.build();
	}
}
