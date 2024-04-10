package telran.probes.service;

import telran.accounting.dto.AccountDto;

public interface AccountProviderService {
	AccountDto getAccount(String email);
}
