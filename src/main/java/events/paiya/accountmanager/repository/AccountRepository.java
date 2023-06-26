package events.paiya.accountmanager.repository;

import events.paiya.accountmanager.domain.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {
}
