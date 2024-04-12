package telran.probes.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.probes.dto.AccountDto;
import telran.probes.model.Account;
import telran.exceptions.NotFoundException;
import telran.probes.repo.AccountProviderRepo;
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountProviderServiceImpl implements AccountProviderService {
final AccountProviderRepo providerRepo;
	@Override
	public AccountDto getAccount(String email) {
		Account account = providerRepo.findById(email)
				.orElseThrow(()-> new NotFoundException(String.format("account with id: {} doesn't exists",email)));
		log.debug("account with id: {} was taken", email);
		return account.build();
	}

}
