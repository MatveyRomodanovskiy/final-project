package telran.probes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.exceptions.NotFoundException;
import telran.probes.service.SensorEmailsProviderService;
import static telran.probes.UrlConstants.*;
@RestController
@RequiredArgsConstructor
@Slf4j
public class SensorEmailsProviderController {
	final SensorEmailsProviderService service;
	@GetMapping("${app.emails.provider.url}" + "{id}")
	String[] getSensorEmails(@PathVariable(name="id") long id) {
		String[] sensorEmails =  service.getSensorEmails(id);
		log.debug("sensor list Emails size received is {}", sensorEmails.length);
		return sensorEmails;
	}
	
	@ExceptionHandler(NotFoundException.class)
	ResponseEntity<String> notFoundHandler(NotFoundException e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
}