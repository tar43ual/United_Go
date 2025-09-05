package com.ual.utility;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.ual.exception.UnitedGoException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ControllerExceptionHandler {
	
	@Autowired
	Environment env;
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Error> exceptionHandler1(ConstraintViolationException exception) {
		Error errmsg = new Error();

		errmsg.setErrCode(HttpStatus.BAD_REQUEST.value());
		String msg = exception.getConstraintViolations().stream().map(ex -> ex.getMessage())
				.collect(Collectors.joining(","));
		errmsg.setErrMessage(msg);
		errmsg.setTimeStamp(LocalDateTime.now());

		ResponseEntity<Error> response = new ResponseEntity<Error>((errmsg), HttpStatus.BAD_REQUEST);
		return response;
	}
	
	@ExceptionHandler(UnitedGoException.class)
	public ResponseEntity<Error> exceptionHandler2(UnitedGoException ex){
		Error err = new Error();
		err.setErrMessage(env.getProperty( ex.getMessage()));
		err.setErrCode(HttpStatus.BAD_REQUEST.value());
		err.setTimeStamp(LocalDateTime.now());
		ResponseEntity<Error> response = new ResponseEntity<Error> (err,HttpStatus.BAD_REQUEST);
		return response;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Error> exceptionHandler3(MethodArgumentNotValidException ex){
		Error err = new Error();
//		err.setErrMessage(ex.getMessage());
		err.setErrMessage(ex.getBindingResult().getAllErrors().stream().map(a -> a.getDefaultMessage()).collect(Collectors.joining(",")));
		err.setErrCode(HttpStatus.BAD_REQUEST.value());
		err.setTimeStamp(LocalDateTime.now());
		ResponseEntity<Error> response = new ResponseEntity<Error> (err,HttpStatus.BAD_REQUEST);
		return response;
	}
	
	@ExceptionHandler(HandlerMethodValidationException.class)
	public ResponseEntity<Error> exceptionHandler4(HandlerMethodValidationException ex){
		Error err = new Error();
		err.setErrMessage(ex.getMessage());
		err.setErrCode(HttpStatus.BAD_REQUEST.value());
		err.setTimeStamp(LocalDateTime.now());
		ResponseEntity<Error> response = new ResponseEntity<Error> (err,HttpStatus.BAD_REQUEST);
		return response;
	}
	
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<Error> exceptionHandler5(NoResourceFoundException ex){
		Error err = new Error();
		err.setErrMessage(ex.getMessage());
		err.setErrCode(HttpStatus.BAD_REQUEST.value());
		err.setTimeStamp(LocalDateTime.now());
		ResponseEntity<Error> response = new ResponseEntity<Error> (err,HttpStatus.BAD_REQUEST);
		return response;
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Error> exceptionHandler6(MethodArgumentTypeMismatchException ex){
		Error err = new Error();
		err.setErrMessage(ex.getMessage());
		err.setErrCode(HttpStatus.BAD_REQUEST.value());
		err.setTimeStamp(LocalDateTime.now());
		ResponseEntity<Error> response = new ResponseEntity<Error> (err,HttpStatus.BAD_REQUEST);
		return response;
	}
}
