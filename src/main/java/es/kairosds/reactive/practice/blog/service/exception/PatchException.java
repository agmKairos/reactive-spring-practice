package es.kairosds.reactive.practice.blog.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PatchException extends ResponseStatusException {

	  private static final long serialVersionUID = 1L;

	  /**
	   * Constructor with a reason to add to the exception message as explanation.
	   *
	   * @param reason the associated reason (optional)
	   */
	  public PatchException(String reason) {
	    this(reason, null);
	  }

	  /**
	   * Constructor with a reason to add to the exception message as explanation, as well as a nested
	   * exception.
	   *
	   * @param reason the associated reason (optional)
	   * @param cause a nested exception (optional)
	   */
	  public PatchException(String reason, Throwable cause) {
	    this(HttpStatus.BAD_REQUEST, reason, cause);
	  }

	  /**
	   * Constructor with a response status and a reason to add to the exception message as explanation,
	   * as well as a nested exception.
	   *
	   * @param status the HTTP status
	   * @param reason the associated reason
	   * @param cause a nested exception
	   */
	  public PatchException(HttpStatus status, String reason, Throwable cause) {
	    super(status, reason, cause);
	  }
}
