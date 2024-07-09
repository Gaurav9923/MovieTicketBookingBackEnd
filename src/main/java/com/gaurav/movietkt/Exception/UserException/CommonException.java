package com.gaurav.movietkt.Exception.UserException;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class CommonException {
	private final String message;
    private final Throwable throwable;
    private final HttpStatus httpStatus;
}
