package events.paiya.accountmanager.services;

import events.paiya.accountmanager.domains.User;
import events.paiya.accountmanager.exceptions.UserAlreadyExistException;
import org.springframework.data.domain.Page;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

public interface UserService {
    boolean isUserExist(String userId);
    User findByUserId(String userId) throws HttpClientErrorException.NotFound;
    List<User> findAllUser();
    Page<User> findPaginatedUserList(int page, int size);
    User createUser(User user) throws UserAlreadyExistException;
    User updateUser(User user);
    void deleteUser(String userId);
    User changeUserAccountActiveStatus(String userId, boolean status);

    void deleteAll();
}
