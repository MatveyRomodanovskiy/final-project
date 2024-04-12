package telran.probes.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.probes.model.Account;

public interface AccountProviderRepo extends MongoRepository<Account, String> {



}
