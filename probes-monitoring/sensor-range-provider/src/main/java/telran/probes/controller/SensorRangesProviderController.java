//package telran.probes.controller;
//
//import org.springframework.web.bind.annotation.*;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import telran.probes.dto.Range;
//import telran.probes.service.SensorRangeProviderService;
//import static telran.probes.UrlConstants.*;
//@RestController
//@RequiredArgsConstructor
//@Slf4j
//public class SensorRangesProviderController {
//	final SensorRangeProviderService service;
//	@GetMapping("${app.range.provider.url}" + "{id}")
//	Range getSensorRange(@PathVariable(name="id") long id) {
//		Range sensorRange =  service.getSensorRange(id);
//		log.debug("sensor range received is {}", sensorRange);
//		return sensorRange;
//	}
//	
//	
//}
package telran.probes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.exceptions.NotFoundException;
import telran.exceptions.SensorNotFoundException;
import telran.probes.dto.Range;
import telran.probes.service.SensorRangeProviderService;
@RestController
@RequiredArgsConstructor
@Slf4j
public class SensorRangesProviderController {
	final SensorRangeProviderService service;
	@GetMapping("${app.range.provider.url}" + "/{id}")
	Range getSensorRange(@PathVariable(name="id") long id) {
		Range sensorRange =  service.getSensorRange(id);
		log.debug("sensor range received is {}", sensorRange);
		return sensorRange;
	}
	
	@ExceptionHandler(SensorNotFoundException.class)
	ResponseEntity<String> notFoundHandler(NotFoundException e) {
		return returnResponse(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	private ResponseEntity<String> returnResponse(String message, HttpStatus status) {
		log.error(message);
		return new ResponseEntity<String>(message, status);
	}
	
}