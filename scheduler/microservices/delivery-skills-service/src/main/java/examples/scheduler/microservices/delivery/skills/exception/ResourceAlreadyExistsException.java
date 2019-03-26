package examples.scheduler.microservices.delivery.skills.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 4086346611853416299L;

	public ResourceAlreadyExistsException() {
		super();
	}

	public ResourceAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ResourceAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceAlreadyExistsException(String message) {
		super(message);
	}

	public ResourceAlreadyExistsException(Throwable cause) {
		super(cause);
	}

}