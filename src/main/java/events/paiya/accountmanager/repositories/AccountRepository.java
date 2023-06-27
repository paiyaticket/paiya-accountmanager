package events.paiya.accountmanager.repositories;

import events.paiya.accountmanager.domains.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {
}
