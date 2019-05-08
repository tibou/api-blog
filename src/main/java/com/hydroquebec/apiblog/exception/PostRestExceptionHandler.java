package com.hydroquebec.apiblog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PostRestExceptionHandler {

	//Add exception handler for comment not found
	
	@ExceptionHandler
	public ResponseEntity<CommentErrorResponse> handleException(CommentNotFoundException exc) {
				
		CommentErrorResponse error = new CommentErrorResponse(
											HttpStatus.NOT_FOUND.value(),
											exc.getMessage(),
											System.currentTimeMillis());
				
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	
	// Add another exception handler ... to catch any exception (catch all)

	@ExceptionHandler
	public ResponseEntity<CommentErrorResponse> handleException(Exception exc) {
		
		CommentErrorResponse error = new CommentErrorResponse(
											HttpStatus.BAD_REQUEST.value(),
											exc.getMessage(),
				System.currentTimeMillis());
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
}





