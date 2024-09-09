package com.brijframework.content.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code =  HttpStatus.CONFLICT)
public class InvalidParameterException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public InvalidParameterException() {
		// TODO Auto-generated constructor stub
	}
	
	public InvalidParameterException(String messege) {
		super(messege);
	}
	
}
