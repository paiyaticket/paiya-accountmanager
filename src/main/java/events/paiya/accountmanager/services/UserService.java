package events.paiya.accountmanager.services;

import events.paiya.accountmanager.domains.User;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByUserId(String userId) throws HttpClientErrorException.NotFound;
    List<User> findAllUser();
    List<User> findPaginatedUserList();
    User createUser(User user);
    User updateUser(String userId);
    void deleteUser(String userId);
    void disableUserAccount(String userId);
    void enableUserAccount(String userId);
}
