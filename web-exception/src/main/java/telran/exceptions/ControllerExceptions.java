package telran.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;


@ControllerAdvice
@Slf4j
public class ControllerExceptions {
	public static String TYPE_MISMATCH_MESSAGE = "URL parameter has type mismatch";
	public static String JSON_TYPE_MISMATCH_MESSAGE = "JSON contains field with type mismatch";	
@ExceptionHandler(SensorNotFoundException.class)
ResponseEntity<String> notFoundHandler(NotFoundException e) {
	return returnResponse(e.getMessage(), HttpStatus.NOT_FOUND);
}
private ResponseEntity<String> returnResponse(String message, HttpStatus status) {
	log.error(message);
	return new ResponseEntity<String>(message, status);
}
}
