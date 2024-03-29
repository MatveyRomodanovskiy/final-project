package telran.probes.controller;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.probes.service.SensorEmailsProviderService;
import static telran.probes.UrlConstants.*;
@RestController
@RequiredArgsConstructor
@Slf4j
public class SensorEmailsProviderController {
	final SensorEmailsProviderService service;
	@GetMapping(EMAILS_PATH + "{id}")
	String[] getSensorEmails(@PathVariable(name="id") long id) {
		String[] sensorEmails =  service.getSensorEmails(id);
		log.debug("sensor list Emails size received is {}", sensorEmails.length);
		return sensorEmails;
	}
	
	
}