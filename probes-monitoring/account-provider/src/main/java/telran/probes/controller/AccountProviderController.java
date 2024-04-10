package telran.probes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import telran.accounting.dto.AccountDto;
import telran.exceptions.NotFoundException;
import telran.probes.service.AccountProviderService;

@Service
@RestController
@RequestMapping("accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountProviderController {
	final AccountProviderService providerService;
	
	
	@GetMapping("{mail}")
	AccountDto deleteAccount (@PathVariable @Valid @NotNull String mail) {
		AccountDto accountDto = providerService.getAccount(mail);
		log.debug("Provider controller recieved account with id{}", mail);
		return accountDto;	
	}
	
	@ExceptionHandler(NotFoundException.class)
	ResponseEntity<String> notFoundHandler(NotFoundException e) {
		return returnResponse(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	private ResponseEntity<String> returnResponse(String message, HttpStatus status) {
		log.error(message);
		return new ResponseEntity<String>(message, status);
	}

	@ExceptionHandler(IllegalStateException.class)
	ResponseEntity<String> illegalStateHandler(IllegalStateException e) {
		return returnResponse(e.getMessage(), HttpStatus.NOT_FOUND);
	}

}





