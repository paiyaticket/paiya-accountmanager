package events.paiya.accountmanager.repositories;

import events.paiya.accountmanager.domains.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
