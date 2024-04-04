package telran.probes.controller;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.exceptions.NotFoundException;
import telran.exceptions.SensorNotFoundException;
import telran.probes.service.SensorEmailsProviderService;
@RestController
@RequiredArgsConstructor
@Slf4j
public class SensorEmailsProviderController {
final SensorEmailsProviderService providerService;
@GetMapping("${app.emails.provider.url}" + "/{id}")
String[] getEmails(@PathVariable(name="id") long id) {
	String[] emails =  providerService.getSensorEmails(id);
	log.debug("emails received are {}", Arrays.deepToString(emails));
	return emails;
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