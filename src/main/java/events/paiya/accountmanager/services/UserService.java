package events.paiya.accountmanager.services;

import events.paiya.accountmanager.domains.User;
import org.springframework.data.domain.Page;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByUserId(String userId) throws HttpClientErrorException.NotFound;
    List<User> findAllUser();
    Page<User> findPaginatedUserList(int page, int size);
    User createUser(User user);
    User updateUser(String userId, User user);
    void deleteUser(String userId);
    void disableUserAccount(String userId);
    void enableUserAccount(String userId);
}
