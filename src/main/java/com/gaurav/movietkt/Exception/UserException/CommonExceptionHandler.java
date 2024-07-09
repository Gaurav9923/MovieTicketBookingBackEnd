package com.gaurav.movietkt.Exception.UserException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {

	@ExceptionHandler(value = {NotFoundException.class})
	public ResponseEntity<?> handleUserNotFoundException(NotFoundException userNotFoundException){
		CommonException commonException = new CommonException(
				userNotFoundException.getMessage(),
				userNotFoundException.getCause(),
				HttpStatus.NOT_FOUND
	        );

	        return new ResponseEntity<>(commonException  , HttpStatus.NOT_FOUND);
	}
}
